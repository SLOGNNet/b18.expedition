package com.bridge18.expedition.repository;

import akka.Done;
import com.bridge18.expedition.entities.driver.DriverCreated;
import com.bridge18.expedition.entities.driver.DriverEvent;
import com.bridge18.expedition.entities.driver.DriverUpdated;
import com.datastax.driver.core.*;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.pcollections.PSequence;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static com.bridge18.expedition.core.CompletionStageUtils.*;
import static com.bridge18.expedition.core.CassandraReadSideUtils.*;

@Singleton
public class DriverRepositoryV1 {
    private final CassandraSession session;

    @Inject
    public DriverRepositoryV1(CassandraSession session, ReadSide readSide) {
        this.session = session;
        readSide.register(DriverEventProcessor.class);
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
                    .setEventHandler(DriverCreated.class,
                            e -> insertDriverSummary(e.getId(), e.getFirstName().orElse(null),
                                    e.getLastName().orElse(null)))
                    .setEventHandler(DriverUpdated.class,
                            e -> updateDriverSummary(e.getId(), e.getFirstName().orElse(null),
                                    e.getLastName().orElse(null)))
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
