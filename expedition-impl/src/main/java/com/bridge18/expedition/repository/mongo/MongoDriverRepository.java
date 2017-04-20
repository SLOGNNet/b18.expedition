package com.bridge18.expedition.repository.mongo;

import akka.Done;
import com.bridge18.expedition.entities.driver.DriverCreated;
import com.bridge18.expedition.entities.driver.DriverEvent;
import com.bridge18.expedition.entities.driver.DriverUpdated;
import com.bridge18.readside.mongodb.readside.MongodbReadSide;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Sort;
import org.pcollections.PSequence;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.bridge18.expedition.core.CompletionStageUtils.doAll;

@Singleton
public class MongoDriverRepository {
    @Inject
    public MongoDriverRepository(ReadSide readSide) {
        readSide.register(DriverEventProcessor.class);
    }

    private static class DriverEventProcessor extends ReadSideProcessor<DriverEvent> {

        private final MongodbReadSide readSide;

        @Inject
        public DriverEventProcessor(MongodbReadSide readSide) {
            this.readSide = readSide;
        }

        @Override
        public ReadSideHandler<DriverEvent> buildHandler() {
            return readSide.<DriverEvent>builder("mongoDriverEventOffset")
                    .setGlobalPrepare(this::globalPrepare)
                    .setPrepare(this::prepareStatements)
                    .setEventHandler(DriverCreated.class,
                            (datastore, e) -> insertDriverSummary(datastore, e.getId(), e.getFirstName().orElse(null),
                                    e.getLastName().orElse(null)))
                    .setEventHandler(DriverUpdated.class,
                            (datastore, e) -> updateDriverSummary(datastore, e.getId(), e.getFirstName().orElse(null),
                                    e.getLastName().orElse(null)))
                    .build();
        }

        @Override
        public PSequence<AggregateEventTag<DriverEvent>> aggregateTags() {
            return DriverEvent.TAG.allTags();
        }

        private CompletionStage<Done> globalPrepare(Datastore datastore){
            return doAll(
                    //@TODO: indexing?

                    CompletableFuture.runAsync(() -> {
                        datastore.ensureIndexes(Driver.class);
                    })
            );
        }

        private CompletionStage<Done> prepareStatements(Datastore datastore, AggregateEventTag<DriverEvent> tag){
            return doAll(
                    //@TODO: indexing?
            );
        }

        private CompletionStage<Void> insertDriverSummary(Datastore datastore,
                                                                          String driverId, String firstName, String lastName){

            return CompletableFuture.runAsync(() -> {
                datastore.save(new Driver(driverId, firstName, lastName));
            });
        }

        private CompletionStage<Void> updateDriverSummary(Datastore datastore,
                                                                          String driverId, String firstName, String lastName){

            return CompletableFuture.runAsync(() -> {
                datastore.find(Driver.class).field("rides").greaterThan(10).order(Sort.ascending("driverId"));

                datastore.update(
                        datastore.createQuery(Driver.class).field("driverId").equal(driverId),
                        datastore.createUpdateOperations(Driver.class)
                                .set("firstName", firstName)
                                .set("lastName", lastName)
                    );
            });
        }
    }
}