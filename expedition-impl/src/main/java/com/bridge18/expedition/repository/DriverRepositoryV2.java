package com.bridge18.expedition.repository;

import akka.Done;
import com.bridge18.expedition.dto.v1.ContactInfoDTO;
import com.bridge18.expedition.dto.v1.DriverSummary;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.driver.*;
import com.datastax.driver.core.*;
import com.datastax.driver.extras.codecs.arrays.ObjectArrayCodec;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import com.datastax.driver.extras.codecs.json.JacksonJsonCodec;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.pcollections.PSequence;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.bridge18.expedition.core.CompletionStageUtils.*;
import static com.bridge18.expedition.core.CassandraReadSideUtils.*;

@Singleton
public class DriverRepositoryV2 {
    private final CassandraSession session;

    @Inject
    public DriverRepositoryV2(CassandraSession session, ReadSide readSide) {
        this.session = session;
        readSide.register(DriverEventProcessor.class);
    }

    public CompletionStage<PaginatedSequence<DriverSummary>> getDrivers(String pagingState, int pageSize){
        return countDrivers()
                .thenCompose(
                        count -> {
                            CompletionStage<PaginatedSequence<DriverSummary>> driverSummaries =
                                    selectDrivers(pagingState, pageSize, count);

                            return driverSummaries;
                        }
                );
    }

    private CompletionStage<Integer> countDrivers(){
        return session
                .selectOne(
                        "SELECT COUNT(*) FROM driverSummary_v2"
                )
                .thenApply(row -> (int) row.get().getLong("count"));
    }

    private CompletionStage<PaginatedSequence<DriverSummary>> selectDrivers(String pagingState, int pageSize, int count){
        return session
                .underlying()
                .thenApply(nativeSession -> {
                    Statement queryStatement = new SimpleStatement("SELECT * FROM driverSummary_v2");
                    queryStatement.setFetchSize(pageSize);
                    if(pagingState != null){
                        queryStatement.setPagingState(PagingState.fromString(pagingState));
                    }
                    return nativeSession.execute(queryStatement);
                })
                .thenApply(resultSet -> {
                            List<DriverSummary> resultList = resultSet.all().stream()
                                    .map(DriverRepositoryV2::convertDriverSummary)
                                    .collect(Collectors.toList());
                            return new PaginatedSequence<DriverSummary>(
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

    private static DriverSummary convertDriverSummary(Row driver) {
        return new DriverSummary(
                driver.getString("driverId"),
                driver.getString("firstName"),
                driver.getString("lastName"),
                driver.getList("contactInfoList", ContactInfoDTO.class)
        );
    }



    private static class DriverEventProcessor extends ReadSideProcessor<DriverEvent> {

        private final CassandraSession session;
        private final CassandraReadSide readSide;

        private PreparedStatement insertDriverSummaryStatement;
        private PreparedStatement updateDriverSummaryStatement;
        private PreparedStatement deleteDriverSummaryStatement;

        @Inject
        public DriverEventProcessor(CassandraSession session, CassandraReadSide readSide) {
            this.session = session;
            this.readSide = readSide;
        }

        @Override
        public ReadSideHandler<DriverEvent> buildHandler() {
            return readSide.<DriverEvent>builder("driverEventOffset_v2")
                    .setGlobalPrepare(this::createTables)
                    .setPrepare(tag -> prepareStatements())
                    .setEventHandler(DriverCreated.class,
                            e -> insertDriverSummary(e.getId(), e.getFirstName().orElse(null),
                                    e.getLastName().orElse(null),
                                    transformPVectorToList(e.getContactInfo())
                            ))
                    .setEventHandler(DriverUpdated.class,
                            e -> updateDriverSummary(e.getId(), e.getFirstName().orElse(null),
                                    e.getLastName().orElse(null),
                                    transformPVectorToList(e.getContactInfo())
                            ))
                    .setEventHandler(DriverDeleted.class,
                            e -> deleteDriverSummary(e.getId()))
                    .build();
        }

        private List<ContactInfoDTO> transformPVectorToList(Optional<PVector<ContactInfo>> contactInfoPVector){
            if(!contactInfoPVector.isPresent() || contactInfoPVector.get().isEmpty()){
                return new ArrayList<>();
            }

            return contactInfoPVector.get().stream()
                    .map(contactInfo -> new ContactInfoDTO(
                            contactInfo.getLabel().orElse(""),
                            contactInfo.getValue().orElse(""),
                            contactInfo.getType().orElse(ContactInfoType.NONE)
                    ))
                    .collect(Collectors.toList());
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
                    ),

                    session.executeCreateTable(
                            "CREATE TABLE IF NOT EXISTS driverSummary_v2 (" +
                                    "driverId text PRIMARY KEY, " +
                                    "firstName text, " +
                                    "lastName text," +
                                    "contactInfoList list<varchar>" +
                                    ")"
                    )

            );
        }

        private CompletionStage<Done> prepareStatements(){
            return doAll(
                    session.underlying()
                            .thenAccept(s -> {
                                registerCodec(s, new EnumNameCodec<>(ContactInfoType.class));
                                registerCodec(s, new JacksonJsonCodec<>(ContactInfoDTO.class));
                            })
                            .thenApply(x -> Done.getInstance()),
                    prepareInsertDriverSummaryStatement(),
                    prepareUpdateDriverSummaryStatement(),
                    prepareDeleteDriverSummaryStatement()
            );
        }

        private void registerCodec(Session session, TypeCodec<?> codec) {
            session.getCluster().getConfiguration().getCodecRegistry().register(codec);
        }

        private CompletionStage<Done> prepareInsertDriverSummaryStatement(){
            return session
                    .prepare("INSERT INTO driverSummary_v2(" +
                            "driverId, " +
                            "firstName, " +
                            "lastName, " +
                            "contactInfoList" +
                            ") VALUES (?, ?, ?, ?)")
                    .thenApply(accept(s -> insertDriverSummaryStatement = s));
        }

        private CompletionStage<Done> prepareUpdateDriverSummaryStatement(){
            return session
                    .prepare("UPDATE driverSummary_v2 " +
                            "SET firstName = ?, " +
                            "lastName = ?, " +
                            "contactInfoList = ?" +
                            "WHERE driverId = ?")
                    .thenApply(accept(s -> updateDriverSummaryStatement = s));
        }

        private CompletionStage<Done> prepareDeleteDriverSummaryStatement(){
            return session
                    .prepare("DELETE FROM driverSummary_v2 " +
                            "WHERE driverId = ?")
                    .thenApply(accept(s -> deleteDriverSummaryStatement = s));
        }

        private CompletionStage<List<BoundStatement>> insertDriverSummary(String driverId, String firstName, String lastName, List<ContactInfoDTO> contactInfoList){
            return completedStatements(
                    insertDriverSummaryStatement.bind(
                            driverId, firstName, lastName, contactInfoList
                    )
            );
        }

        private CompletionStage<List<BoundStatement>> updateDriverSummary(String driverId, String firstName, String lastName, List<ContactInfoDTO> contactInfoList){
            return completedStatements(
                    updateDriverSummaryStatement.bind(
                            firstName, lastName, contactInfoList, driverId
                    )
            );
        }

        private CompletionStage<List<BoundStatement>> deleteDriverSummary(String driverId){
            return completedStatements(
                    deleteDriverSummaryStatement.bind(
                            driverId
                    )
            );
        }
    }
}
