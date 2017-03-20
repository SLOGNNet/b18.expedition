package com.bridge18.expedition.repository;

import akka.Done;
import com.bridge18.expedition.dto.v1.EquipmentSummary;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.equipment.EquipmentEvent;
import com.bridge18.expedition.entities.equipment.EquipmentSubType;
import com.bridge18.expedition.entities.equipment.EquipmentType;
import com.datastax.driver.core.*;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
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

    public CompletionStage<PaginatedSequence<EquipmentSummary>> getEquipments(String pagingState, int pageSize){
        return countEquipments()
                .thenCompose(
                        count -> {
                            CompletionStage<PaginatedSequence<EquipmentSummary>> equipmentSummaries =
                                    selectEquipments(pagingState, pageSize, count);

                            return equipmentSummaries;
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

    private CompletionStage<PaginatedSequence<EquipmentSummary>> selectEquipments(String pagingState, int pageSize, int count){
        return session
                .underlying()
                .thenApply(nativeSession -> {
                    Statement queryStatement = new SimpleStatement("SELECT * FROM equipmentSummary");
                    queryStatement.setFetchSize(pageSize);
                    if(pagingState != null){
                        queryStatement.setPagingState(PagingState.fromString(pagingState));
                    }
                    return nativeSession.execute(queryStatement);
                })
                .thenApply(resultSet -> {
                            List<EquipmentSummary> resultList = resultSet.all().stream()
                                    .map(EquipmentRepository::convertEquipmentSummary)
                                    .collect(Collectors.toList());
                            return new PaginatedSequence<EquipmentSummary>(
                                    TreePVector.from(resultList).subList(0, pageSize > resultList.size() ?
                                            resultList.size() : pageSize)
                                    ,
                                    resultSet.getAllExecutionInfo().get(0).getPagingState() == null ?
                                            null :
                                            resultSet.getAllExecutionInfo().get(0).getPagingState().toString(),
                                    pageSize,
                                    count);
                        }
                );

    }

    private static EquipmentSummary convertEquipmentSummary(Row equipment) {
        return new EquipmentSummary(
                equipment.getString("equipmentId"),
                equipment.getString("vin"),
                equipment.get("type", EquipmentType.class),
                equipment.get("subType", EquipmentSubType.class)
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
                                    e.getType().get(), e.getSubType().get()))
                    .setEventHandler(EquipmentEvent.EquipmentUpdated.class,
                            e -> updateEquipmentSummary(e.getEquipmentId(), e.getVin().orElse(null),
                                    e.getType().get(), e.getSubType().get()))
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
                                    "type text, " +
                                    "subType text" +
                                    ");"
                    )
            );
        }

        private CompletionStage<Done> prepareStatements(){
            return doAll(
                    session.underlying()
                            .thenAccept(s -> {
                                registerCodec(s, new EnumNameCodec<>(EquipmentType.class));
                                registerCodec(s, new EnumNameCodec<>(EquipmentSubType.class));
                            })
                            .thenApply(x -> Done.getInstance()),
                    prepareInsertEquipmentSummaryStatement(),
                    prepareUpdateEquipmentSummaryStatement(),
                    prepareDeleteEquipmentSummaryStatement()
            );
        }

        private void registerCodec(Session session, TypeCodec<?> codec) {
            session.getCluster().getConfiguration().getCodecRegistry().register(codec);
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

        private CompletionStage<List<BoundStatement>> insertEquipmentSummary(String equipmentId, String vin, EquipmentType type, EquipmentSubType subType){
            return completedStatements(
                    insertEquipmentSummaryStatement.bind(
                            equipmentId, vin, type, subType
                    )
            );
        }

        private CompletionStage<List<BoundStatement>> updateEquipmentSummary(String equipmentId, String vin, EquipmentType type, EquipmentSubType subType){
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
