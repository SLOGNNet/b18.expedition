package com.bridge18.expedition.repository.mongo.equipment;

import akka.Done;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.equipment.*;
import com.bridge18.expedition.repository.EquipmentRepository;
import com.bridge18.readside.mongodb.readside.MongodbReadSide;
import com.google.inject.Inject;
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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.bridge18.core.CompletionStageUtils.doAll;

public class MongoEquipmentRepository implements EquipmentRepository {
    private Datastore datastore;

    @Inject
    public MongoEquipmentRepository(ReadSide readSide, Datastore datastore) {
        readSide.register(EquipmentEventProcessor.class);
        this.datastore = datastore;
    }

    @Override
    public PaginatedSequence<EquipmentState> getEquipments(int pageNumber, int pageSize) {
        List<EquipmentState> equipments = datastore.createQuery(EquipmentState.class)
                .order(Sort.naturalDescending())
                .asList(new FindOptions()
                        .skip(pageNumber > 0 ? (pageNumber - 1) * pageSize : 0)
                        .limit(pageSize)
                );
        return new PaginatedSequence<>(
                TreePVector.from(
                        equipments
                ),
                pageSize,
                (int) datastore.getCount(datastore.createQuery(EquipmentState.class))
        );
    }

    private static class EquipmentEventProcessor extends ReadSideProcessor<EquipmentEvent> {

        private final MongodbReadSide readSide;

        @Inject
        public EquipmentEventProcessor(MongodbReadSide readSide) {
            this.readSide = readSide;
        }

        @Override
        public ReadSideHandler<EquipmentEvent> buildHandler() {
            return readSide.<EquipmentEvent>builder("mongoEquipmentEventOffset")
                    .setGlobalPrepare(this::globalPrepare)
                    .setEventHandler(EquipmentCreated.class,
                            this::insertEquipmentSummary
                    )
                    .setEventHandler(EquipmentUpdated.class,
                            this::updateEquipmentSummary
                    )
                    .setEventHandler(EquipmentDeleted.class,
                            this::deleteEquipmentSummary
                    )
                    .build();
        }

        @Override
        public PSequence<AggregateEventTag<EquipmentEvent>> aggregateTags() {
            return EquipmentEvent.TAG.allTags();
        }

        private CompletionStage<Done> globalPrepare(Datastore datastore) {
            return doAll(
                    CompletableFuture.runAsync(() -> {
                        datastore.ensureIndexes(EquipmentState.class);
                    })
            );
        }

        private CompletionStage<Void> insertEquipmentSummary(Datastore datastore,
                                                             EquipmentCreated e) {

            return CompletableFuture.runAsync(() -> {
                datastore.save(
                        createEquipmentState(e)
                );
            });
        }

        private EquipmentState createEquipmentState(EquipmentCreated e) {
            EquipmentState.Builder builder = EquipmentState.builder().id(e.getId());

            if (e.getVin().isPresent()) builder.vin(e.getVin().get());
            if (e.getOwnership().isPresent()) builder.ownership(e.getOwnership().get());
            if (e.getType().isPresent()) builder.type(e.getType().get());
            if (e.getSubType().isPresent()) builder.subType(e.getSubType().get());
            if (e.getOperatingMode().isPresent()) builder.operatingMode(e.getOperatingMode().get());
            if (e.getMake().isPresent()) builder.make(e.getMake().get());
            if (e.getModel().isPresent()) builder.model(e.getModel().get());
            if (e.getColour().isPresent()) builder.colour(e.getColour().get());
            if (e.getIsSleeperBerthAvailable().isPresent())
                builder.isSleeperBerthAvailable(e.getIsSleeperBerthAvailable().get());
            if (e.getNumber().isPresent()) builder.number(e.getNumber().get());
            if (e.getLicensePlateState().isPresent()) builder.licensePlateState(e.getLicensePlateState().get());
            if (e.getLicensePlateNumber().isPresent()) builder.licensePlateNumber(e.getLicensePlateNumber().get());
            if (e.getLicensePlateExpiration().isPresent())
                builder.licensePlateExpiration(e.getLicensePlateExpiration().get());
            if (e.getNotes().isPresent()) builder.notes(e.getNotes().get());
            if (e.getMileageRecords().isPresent()) builder.mileageRecords(e.getMileageRecords().get());

            return builder.build();
        }

        private CompletionStage<Void> updateEquipmentSummary(Datastore datastore,
                                                             EquipmentUpdated e) {

            return CompletableFuture.runAsync(() -> {
                UpdateOperations<EquipmentState> updateOperations = setNotNullFieldsInUpdateOperations(datastore, e);

                datastore.update(
                        datastore.createQuery(EquipmentState.class).field("id").equal(e.getId()),
                        updateOperations
                );
            });
        }

        private UpdateOperations<EquipmentState> setNotNullFieldsInUpdateOperations(Datastore datastore, EquipmentUpdated e) {
            UpdateOperations updateOperations = datastore.createUpdateOperations(EquipmentState.class);

            if (e.getVin().isPresent()) {
                updateOperations.set("vin", e.getVin().get());
            } else {
                updateOperations.unset("vin");
            }
            if (e.getOwnership().isPresent()) {
                updateOperations.set("ownership", e.getOwnership().get());
            } else {
                updateOperations.unset("ownership");
            }
            if (e.getType().isPresent()) {
                updateOperations.set("type", e.getType().get());
            } else {
                updateOperations.unset("type");
            }
            if (e.getSubType().isPresent()) {
                updateOperations.set("subType", e.getSubType().get());
            } else {
                updateOperations.unset("subType");
            }
            if (e.getOperatingMode().isPresent()) {
                updateOperations.set("operatingMode", e.getOperatingMode().get());
            } else {
                updateOperations.unset("operatingMode");
            }
            if (e.getMake().isPresent()) {
                updateOperations.set("make", e.getMake().get());
            } else {
                updateOperations.unset("make");
            }
            if (e.getModel().isPresent()) {
                updateOperations.set("model", e.getModel().get());
            } else {
                updateOperations.unset("model");
            }
            if (e.getColour().isPresent()) {
                updateOperations.set("colour", e.getColour().get());
            } else {
                updateOperations.unset("colour");
            }
            if (e.getIsSleeperBerthAvailable().isPresent()) {
                updateOperations.set("isSleeperBerthAvailable", e.getIsSleeperBerthAvailable().get());
            } else {
                updateOperations.unset("isSleeperBerthAvailable");
            }
            if (e.getNumber().isPresent()) {
                updateOperations.set("number", e.getNumber().get());
            } else {
                updateOperations.unset("number");
            }
            if (e.getLicensePlateState().isPresent()) {
                updateOperations.set("licensePlateState", e.getLicensePlateState().get());
            } else {
                updateOperations.unset("licensePlateState");
            }
            if (e.getLicensePlateNumber().isPresent()) {
                updateOperations.set("licensePlateNumber", e.getLicensePlateNumber().get());
            } else {
                updateOperations.unset("licensePlateNumber");
            }
            if (e.getLicensePlateExpiration().isPresent()) {
                updateOperations.set("licensePlateExpiration", e.getLicensePlateExpiration().get());
            } else {
                updateOperations.unset("licensePlateExpiration");
            }
            if (e.getNotes().isPresent()) {
                updateOperations.set("notes", e.getNotes().get());
            } else {
                updateOperations.unset("notes");
            }
            if (e.getMileageRecords().isPresent()) {
                updateOperations.set("mileageRecords", e.getMileageRecords().get());
            } else {
                updateOperations.unset("mileageRecords");
            }

            return updateOperations;
        }

        private CompletionStage<Void> deleteEquipmentSummary(Datastore datastore, EquipmentDeleted e) {
            return CompletableFuture.runAsync(() -> {
                        Query<EquipmentState> equipmentsToDelete = datastore.createQuery(EquipmentState.class)
                                .field("id")
                                .equal(e.getId());
                        datastore.delete(equipmentsToDelete);
                    }
            );
        }
    }
}
