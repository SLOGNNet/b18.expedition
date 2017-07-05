package com.bridge18.expedition.repository.mongo.driver;

import akka.Done;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.driver.*;
import com.bridge18.expedition.repository.DriverRepository;
import com.bridge18.readside.mongodb.readside.MongodbReadSide;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;
import org.mongodb.morphia.query.UpdateOperations;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.bridge18.core.CompletionStageUtils.doAll;


@Singleton
public class MongoDriverRepository implements DriverRepository {
    private Datastore datastore;

    @Inject
    public MongoDriverRepository(ReadSide readSide, Datastore datastore) {
        readSide.register(DriverEventProcessor.class);
        this.datastore = datastore;
    }

    @Override
    public PaginatedSequence<DriverState> getDrivers(int pageNumber, int pageSize) {
        List<DriverState> drivers = datastore.createQuery(DriverState.class)
                .order(Sort.naturalDescending())
                .asList(new FindOptions().skip(pageNumber > 0 ? (pageNumber - 1) * pageSize : 0)
                        .limit(pageSize)
                );
        return new PaginatedSequence<>(
                TreePVector.from(drivers),
                pageSize,
                (int) datastore.getCount(datastore.createQuery(DriverState.class))
        );
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
                    .setEventHandler(DriverCreated.class,
                            this::insertDriverSummary
                    )
                    .setEventHandler(DriverUpdated.class,
                            this::updateDriverSummary
                    )
                    .setEventHandler(DriverDeleted.class,
                            this::deleteDriverSummary
                    )
                    .build();
        }

        @Override
        public PSequence<AggregateEventTag<DriverEvent>> aggregateTags() {
            return DriverEvent.TAG.allTags();
        }

        private CompletionStage<Done> globalPrepare(Datastore datastore) {
            return doAll(
                    CompletableFuture.runAsync(() -> {
                        datastore.ensureIndexes(DriverState.class);
                    })
            );
        }

        private CompletionStage<Void> insertDriverSummary(Datastore datastore,
                                                          DriverCreated e) {

            return CompletableFuture.runAsync(() -> {
                datastore.save(
                        createDriverState(e)
                );
            });
        }

        private DriverState createDriverState(DriverCreated e) {
            DriverState.Builder builder = DriverState.builder().id(e.getId());

            if (e.getFirstName().isPresent()) builder.firstName(e.getFirstName().get());
            if (e.getMiddleName().isPresent()) builder.middleName(e.getMiddleName().get());
            if (e.getLastName().isPresent()) builder.lastName(e.getLastName().get());
            if (e.getContactInfo().isPresent()) builder.contactInfo(e.getContactInfo().get());
            if (e.getPosition().isPresent()) builder.position(e.getPosition().get());
            if (e.getBirthDate().isPresent()) builder.birthDate(e.getBirthDate().get());
            if (e.getSsn().isPresent()) builder.ssn(e.getSsn().get());
            if (e.getPaymentOption().isPresent()) builder.paymentOption(e.getPaymentOption().get());
            if (e.getRate().isPresent()) builder.rate(e.getRate().get());
            if (e.getDriverType().isPresent()) builder.driverType(e.getDriverType().get());
            if (e.getAddress().isPresent()) builder.address(e.getAddress().get());
            if (e.getLicense().isPresent()) builder.license(e.getLicense().get());

            return builder.build();
        }

        private CompletionStage<Void> updateDriverSummary(Datastore datastore,
                                                          DriverUpdated e) {

            return CompletableFuture.runAsync(() -> {
                UpdateOperations<DriverState> updateOperations = setUpdateOperations(datastore, e);

                datastore.update(
                        datastore.createQuery(DriverState.class).field("id").equal(e.getId()),
                        updateOperations
                );
            });
        }

        private UpdateOperations<DriverState> setUpdateOperations(Datastore datastore, DriverUpdated e) {
            UpdateOperations updateOperations = datastore.createUpdateOperations(DriverState.class);

            if (e.getFirstName().isPresent()) {
                updateOperations.set("firstName", e.getFirstName().get());
            } else {
                updateOperations.unset("firstName");
            }
            if (e.getMiddleName().isPresent()) {
                updateOperations.set("middleName", e.getMiddleName().get());
            } else {
                updateOperations.unset("middleName");
            }
            if (e.getLastName().isPresent()) {
                updateOperations.set("lastName", e.getLastName().get());
            } else {
                updateOperations.unset("lastName");
            }
            if (e.getContactInfo().isPresent()) {
                updateOperations.set("contactInfo", e.getContactInfo().get());
            } else {
                updateOperations.unset("contactInfo");
            }
            if (e.getPosition().isPresent()) {
                updateOperations.set("position", e.getPosition().get());
            } else {
                updateOperations.unset("position");
            }
            if (e.getBirthDate().isPresent()) {
                updateOperations.set("birthDate", e.getBirthDate().get());
            } else {
                updateOperations.unset("birthDate");
            }
            if (e.getSsn().isPresent()) {
                updateOperations.set("ssn", e.getSsn().get());
            } else {
                updateOperations.unset("ssn");
            }
            if (e.getPaymentOption().isPresent()) {
                updateOperations.set("paymentOption", e.getPaymentOption().get());
            } else {
                updateOperations.unset("paymentOption");
            }
            if (e.getRate().isPresent()) {
                updateOperations.set("rate", e.getRate().get());
            } else {
                updateOperations.unset("rate");
            }
            if (e.getDriverType().isPresent()) {
                updateOperations.set("driverType", e.getDriverType().get());
            } else {
                updateOperations.unset("driverType");
            }
            if (e.getAddress().isPresent()) {
                updateOperations.set("address", e.getAddress().get());
            } else {
                updateOperations.unset("address");
            }
            if (e.getLicense().isPresent()) {
                updateOperations.set("license", e.getLicense().get());
            } else {
                updateOperations.unset("license");
            }


            return updateOperations;
        }

        private CompletionStage<Void> deleteDriverSummary(Datastore datastore, DriverDeleted e) {
            return CompletableFuture.runAsync(() -> {
                        Query<DriverState> driversToDelete = datastore.createQuery(DriverState.class)
                                .field("id")
                                .equal(e.getId());
                        datastore.delete(driversToDelete);
                    }
            );
        }
    }
}