package com.bridge18.expedition.repository;

import akka.Done;
import com.bridge18.expedition.dto.v1.EquipmentSummary;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.equipment.EquipmentEvent;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.bridge18.expedition.core.CompletionStageUtils.*;
import static com.bridge18.expedition.core.CassandraReadSideUtils.*;

@Singleton
public class EquipmentRepository {
    private CassandraSession session;

    @Inject
    public EquipmentRepository(CassandraSession session, ReadSide readSide) {
        this.session = session;
        readSide.register(EquipmentEventProcessor.class);
    }

    public CompletionStage<PaginatedSequence<EquipmentSummary>> getEquipments(int page, int pageSize){
        return countEquipments()
                .thenCompose(
                        count -> {
                            int offset = page * pageSize;
                            int limit = (page + 1) * pageSize;
                            CompletionStage<PSequence<EquipmentSummary>> equipments = offset > count ?
                                    CompletableFuture.completedFuture(TreePVector.empty()) :
                                    selectEquipments(offset, limit);
                            return equipments.thenApply(seq -> new PaginatedSequence<>(seq, page, pageSize, count));
                        }
                );
    }

    private CompletionStage<Integer> countEquipments(){
        return session
                .selectOne(
                        "SELECT COUNT(*) FROM equipmentSummary"
                )
                .thenApply(row -> (int) row.get().getLong("count"));
    }

    private CompletionStage<PSequence<EquipmentSummary>> selectEquipments(long offset, int limit){
        return session
                .selectAll(
                        "SELECT * FROM equipmentSummary LIMIT ?",
                        limit
                )
                .thenApply(List::stream)
                .thenApply(rows -> rows.skip(offset))
                .thenApply(rows -> rows.map(EquipmentRepository::convertEquipmentSummary))
                .thenApply(equipmentSummaries -> equipmentSummaries.collect(Collectors.toList()))
                .thenApply(TreePVector::from);
    }

    private static EquipmentSummary convertEquipmentSummary(Row equipment) {
        return new EquipmentSummary(
                equipment.getString("equipmentId"),
                equipment.getString("vin"),
                equipment.getInt("type"),
                equipment.getInt("subType")
        );
    }


    private static class EquipmentEventProcessor extends ReadSideProcessor<EquipmentEvent>{
        private final CassandraSession session;
        private final CassandraReadSide readSide;

        private PreparedStatement insertEquipmentSummaryStatement;
        private PreparedStatement updateEquipmentSummaryStatement;
        private PreparedStatement deleteEquipmentSummaryStatement;

        @Inject
        public EquipmentEventProcessor(CassandraSession session, CassandraReadSide readSide) {
            this.session = session;
            this.readSide = readSide;
        }

        @Override
        public ReadSideHandler<EquipmentEvent> buildHandler() {
            return readSide.<EquipmentEvent>builder("equipmentEventOffset")
                    .setGlobalPrepare(this::createTables)
                    .setPrepare(tag -> prepareStatements())
                    .setEventHandler(EquipmentEvent.EquipmentCreated.class,
                            e -> insertEquipmentSummary(e.getEquipmentId(), e.getVin().orElse(null),
                                    e.getType().get().ordinal(), e.getSubType().get().ordinal()))
                    .setEventHandler(EquipmentEvent.EquipmentChanged.class,
                            e -> updateEquipmentSummary(e.getEquipmentId(), e.getVin().orElse(null),
                                    e.getType().get().ordinal(), e.getSubType().get().ordinal()))
                    .setEventHandler(EquipmentEvent.EquipmentDeleted.class,
                            e -> deleteEquipmentSummary(e.getEquipmentId()))
                    .build();
        }

        @Override
        public PSequence<AggregateEventTag<EquipmentEvent>> aggregateTags() {
            return EquipmentEvent.TAG.allTags();
        }

        private CompletionStage<Done> createTables(){
            return doAll(
                    session.executeCreateTable(
                   "CREATE TABLE IF NOT EXISTS equipmentSummary (" +
                                    "equipmentId text PRIMARY KEY, " +
                                    "vin text, " +
                                    "type int, " +
                                    "subType int" +
                                    ");"
                    )
            );
        }

        private CompletionStage<Done> prepareStatements(){
            return doAll(
                    prepareInsertEquipmentSummaryStatement(),
                    prepareUpdateEquipmentSummaryStatement(),
                    prepareDeleteEquipmentSummaryStatement()
            );
        }

        private CompletionStage<Done> prepareInsertEquipmentSummaryStatement(){
            return session
                    .prepare("INSERT INTO equipmentSummary(" +
                            "equipmentId, " +
                            "vin, " +
                            "type, " +
                            "subType" +
                            ") VALUES (?, ?, ?, ?)")
                    .thenApply(accept(s -> insertEquipmentSummaryStatement = s));
        }

        private CompletionStage<Done> prepareUpdateEquipmentSummaryStatement(){
            return session
                    .prepare("UPDATE equipmentSummary " +
                            "SET vin = ?, " +
                            "type = ?," +
                            "subType = ?" +
                            "WHERE equipmentId = ?")
                    .thenApply(accept(s -> updateEquipmentSummaryStatement = s));
        }

        private CompletionStage<Done> prepareDeleteEquipmentSummaryStatement(){
            return session
                    .prepare("DELETE FROM equipmentSummary " +
                            "WHERE equipmentId = ?")
                    .thenApply(accept(s -> deleteEquipmentSummaryStatement = s));
        }

        private CompletionStage<List<BoundStatement>> insertEquipmentSummary(String equipmentId, String vin, int type, int subType){
            return completedStatements(
                    insertEquipmentSummaryStatement.bind(
                            equipmentId, vin, type, subType
                    )
            );
        }

        private CompletionStage<List<BoundStatement>> updateEquipmentSummary(String equipmentId, String vin, int type, int subType){
            return completedStatements(
                    updateEquipmentSummaryStatement.bind(
                            vin, type, subType, equipmentId
                    )
            );
        }

        private CompletionStage<List<BoundStatement>> deleteEquipmentSummary(String equipmentId){
            return completedStatements(
                    deleteEquipmentSummaryStatement.bind(
                            equipmentId
                    )
            );
        }
    }
}
