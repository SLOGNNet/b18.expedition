package com.bridge18.expedition.repository;

import akka.Done;
import com.bridge18.expedition.dto.v1.DriverSummary;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.driver.DriverEvent;
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
public class DriverRepository {
    private final CassandraSession session;

    @Inject
    public DriverRepository(CassandraSession session, ReadSide readSide) {
        this.session = session;
        readSide.register(DriverEventProcessor.class);
    }

    public CompletionStage<PaginatedSequence<DriverSummary>> getDrivers(int page, int pageSize){
        return countDrivers()
                .thenCompose(
                        count -> {
                            int offset = page * pageSize;
                            int limit = (page + 1) * pageSize;
                            CompletionStage<PSequence<DriverSummary>> drivers = offset > count ?
                                    CompletableFuture.completedFuture(TreePVector.empty()) :
                                    selectDrivers(offset, limit);
                            return drivers.thenApply(seq -> new PaginatedSequence<>(seq, page, pageSize, count));
                        }
                );
    }

    private CompletionStage<Integer> countDrivers(){
        return session
                .selectOne(
                        "SELECT COUNT(*) FROM driverSummary"
                )
                .thenApply(row -> (int) row.get().getLong("count"));
    }

    private CompletionStage<PSequence<DriverSummary>> selectDrivers(long offset, int limit){
        return session
                .selectAll(
                "SELECT * FROM driverSummary LIMIT ?",
                limit
                )
                .thenApply(List::stream)
                .thenApply(rows -> rows.skip(offset))
                .thenApply(rows -> rows.map(DriverRepository::convertDriverSummary))
                .thenApply(driverSummaries -> driverSummaries.collect(Collectors.toList()))
                .thenApply(TreePVector::from);
    }

    private static DriverSummary convertDriverSummary(Row driver) {
        return new DriverSummary(
                driver.getString("driverId"),
                driver.getString("firstName"),
                driver.getString("lastName")
        );
    }



    private static class DriverEventProcessor extends ReadSideProcessor<DriverEvent> {

        private final CassandraSession session;
        private final CassandraReadSide readSide;

        private PreparedStatement insertDriverSummaryStatement;
        private PreparedStatement updateDriverSummaryStatement;

        @Inject
        public DriverEventProcessor(CassandraSession session, CassandraReadSide readSide) {
            this.session = session;
            this.readSide = readSide;
        }

        @Override
        public ReadSideHandler<DriverEvent> buildHandler() {
            return readSide.<DriverEvent>builder("driverEventOffset")
                    .setGlobalPrepare(this::createTables)
                    .setPrepare(tag -> prepareStatements())
                    .setEventHandler(DriverEvent.DriverCreated.class,
                            e -> insertDriverSummary(e.driverId, e.firstName.orElse(null), e.lastName.orElse(null)))
                    .setEventHandler(DriverEvent.DriverUpdated.class,
                            e -> updateDriverSummary(e.driverId, e.firstName.orElse(null), e.lastName.orElse(null)))
                    .build();
        }

        @Override
        public PSequence<AggregateEventTag<DriverEvent>> aggregateTags() {
            return DriverEvent.TAG.allTags();
        }

        private CompletionStage<Done> createTables(){
            return doAll(
                    session.executeCreateTable(
                            "CREATE TABLE IF NOT EXISTS driverSummary (" +
                                    "driverId text PRIMARY KEY, " +
                                    "firstName text, " +
                                    "lastName text" +
                                    ")"
                    )
            );
        }

        private CompletionStage<Done> prepareStatements(){
            return doAll(
                    prepareInsertDriverSummaryStatement(),
                    prepareUpdateDriverSummaryStatement()
            );
        }

        private CompletionStage<Done> prepareInsertDriverSummaryStatement(){
            return session
                    .prepare("INSERT INTO driverSummary(" +
                            "driverId, " +
                            "firstName, " +
                            "lastName" +
                            ") VALUES (?, ?, ?)")
                    .thenApply(accept(s -> insertDriverSummaryStatement = s));
        }

        private CompletionStage<Done> prepareUpdateDriverSummaryStatement(){
            return session
                    .prepare("UPDATE driverSummary " +
                            "SET firstName = ?, " +
                            "lastName = ?" +
                            "WHERE driverId = ?")
                    .thenApply(accept(s -> updateDriverSummaryStatement = s));
        }

        private CompletionStage<List<BoundStatement>> insertDriverSummary(String driverId, String firstName, String lastName){
            return completedStatements(
                    insertDriverSummaryStatement.bind(
                            driverId, firstName, lastName
                    )
            );
        }

        private CompletionStage<List<BoundStatement>> updateDriverSummary(String driverId, String firstName, String lastName){
            return completedStatements(
                    updateDriverSummaryStatement.bind(
                            firstName, lastName, driverId
                    )
            );
        }
    }
}
