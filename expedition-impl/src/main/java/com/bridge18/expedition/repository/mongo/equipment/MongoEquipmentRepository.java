package com.bridge18.expedition.repository.mongo.equipment;

import akka.Done;
import com.bridge18.expedition.dto.v1.EquipmentDTO;
import com.bridge18.expedition.dto.v1.MileageRecordDTO;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.equipment.*;
import com.bridge18.expedition.repository.EquipmentRepository;
import com.bridge18.readside.mongodb.readside.MongodbReadSide;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.pcollections.PSequence;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.bridge18.expedition.core.CompletionStageUtils.doAll;

public class MongoEquipmentRepository implements EquipmentRepository {
    private Datastore datastore;

    @Inject
    public MongoEquipmentRepository(ReadSide readSide, Datastore datastore) {
        readSide.register(EquipmentEventProcessor.class);
        this.datastore = datastore;
    }

    @Override
    public CompletionStage<PaginatedSequence<EquipmentDTO>> getEquipments(int pageNumber, int pageSize) {
        List<MongoEquipment> equipments = datastore.createQuery(MongoEquipment.class)
                .asList(new FindOptions()
                        .skip(pageNumber > 0 ? (pageNumber - 1) * pageSize : 0)
                        .limit(pageSize)
                );
        return CompletableFuture.completedFuture(
                new PaginatedSequence<>(
                        TreePVector.from(
                                equipments.stream()
                                        .map(this::transformMongoEquipmentToEquipmentDTO)
                                        .collect(Collectors.toList())
                        ),
                        pageSize,
                        (int) datastore.getCount(datastore.createQuery(MongoEquipment.class))
                )
        );
    }

    private EquipmentDTO transformMongoEquipmentToEquipmentDTO(MongoEquipment mongoEquipment) {
        List<MileageRecordDTO> mileageRecordDTOList = mongoEquipment.getMileageRecords() != null ?
                Lists.transform(mongoEquipment.getMileageRecords(), mongoMileageRecord ->
                        new MileageRecordDTO(mongoMileageRecord.getMiles(),
                                mongoMileageRecord.getTakenAt()
                        )
                ) : null;

        return new EquipmentDTO(mongoEquipment.getEquipmentId(),
                mongoEquipment.getVin(), mongoEquipment.getOwnership(),
                mongoEquipment.getType(), mongoEquipment.getSubType(),
                mongoEquipment.getOperatingMode(), mongoEquipment.getMake(),
                mongoEquipment.getModel(), mongoEquipment.getColour(),
                mongoEquipment.getIsSleeperBerthAvailable(), mongoEquipment.getNumber(),
                mongoEquipment.getLicensePlateNumber(), mongoEquipment.getLicensePlateState(),
                mongoEquipment.getLicensePlateExpiration(), mongoEquipment.getNotes(),
                mileageRecordDTOList
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
                    .setPrepare(this::prepareStatements)
                    .setEventHandler(EquipmentCreated.class,
                            this::insertEquipmentSummary
                    )
                    .setEventHandler(EquipmentUpdated.class,
                            this::updateEquipmentSummary
                    )
                    .setEventHandler(EquipmentDeleted.class,
                            (datastore, e) -> deleteEquipmentSummary(datastore, e.getId()))
                    .build();
        }

        private List<MongoMileageRecord> transformPVectorToList(Optional<PVector<MileageRecord>> mileageRecordsPVector) {
            if (!mileageRecordsPVector.isPresent() || mileageRecordsPVector.get().isEmpty()) {
                return new ArrayList<>();
            }

            return mileageRecordsPVector.get().stream()
                    .map(mileageRecord -> new MongoMileageRecord(
                            mileageRecord.getMiles().orElse(""),
                            mileageRecord.getTakenAt().orElse(null)
                    ))
                    .collect(Collectors.toList());
        }

        @Override
        public PSequence<AggregateEventTag<EquipmentEvent>> aggregateTags() {
            return EquipmentEvent.TAG.allTags();
        }

        private CompletionStage<Done> globalPrepare(Datastore datastore) {
            return doAll(
                    //@TODO: indexing?

                    CompletableFuture.runAsync(() -> {
                        datastore.ensureIndexes(MongoEquipment.class);
                    })
            );
        }

        private CompletionStage<Done> prepareStatements(Datastore datastore, AggregateEventTag<EquipmentEvent> tag) {
            return doAll(
                    //@TODO: indexing?
            );
        }

        private CompletionStage<Void> insertEquipmentSummary(Datastore datastore,
                                                             EquipmentCreated e) {

            return CompletableFuture.runAsync(() -> {
                datastore.save(
                        new MongoEquipment(e.getId(), e.getVin().orElse(null),
                                e.getOwnership().orElse(null), e.getType().orElse(null),
                                e.getSubType().orElse(null), e.getOperatingMode().orElse(null),
                                e.getMake().orElse(null), e.getModel().orElse(null),
                                e.getColour().orElse(null), e.getIsSleeperBerthAvailable().orElse(null),
                                e.getNumber().orElse(null), e.getLicensePlateState().orElse(null),
                                e.getLicensePlateNumber().orElse(null), e.getLicensePlateExpiration().orElse(null),
                                e.getNotes().orElse(null), transformPVectorToList(e.getMileageRecords()))
                );
            });
        }

        private CompletionStage<Void> updateEquipmentSummary(Datastore datastore,
                                                             EquipmentUpdated e) {

            return CompletableFuture.runAsync(() -> {
                UpdateOperations<MongoEquipment> updateOperations = setNotNullFieldsInUpdateOperations(datastore, e);

                datastore.update(
                        datastore.createQuery(MongoEquipment.class).field("equipmentId").equal(e.getId()),
                        updateOperations
                );
            });
        }

        private UpdateOperations<MongoEquipment> setNotNullFieldsInUpdateOperations(Datastore datastore, EquipmentUpdated e) {
            UpdateOperations updateOperations = datastore.createUpdateOperations(MongoEquipment.class);

            if(e.getVin().isPresent()) updateOperations.set("vin", e.getVin().get());
            if(e.getOwnership().isPresent()) updateOperations.set("ownership", e.getOwnership().get());
            if(e.getType().isPresent()) updateOperations.set("type", e.getType().get());
            if(e.getSubType().isPresent()) updateOperations.set("subType", e.getSubType().get());
            if(e.getOperatingMode().isPresent()) updateOperations.set("operatingMode", e.getOperatingMode().get());
            if(e.getMake().isPresent()) updateOperations.set("make", e.getMake().get());
            if(e.getModel().isPresent()) updateOperations.set("model", e.getModel().get());
            if(e.getColour().isPresent()) updateOperations.set("colour", e.getColour().get());
            if(e.getIsSleeperBerthAvailable().isPresent()) updateOperations.set("isSleeperBerthAvailable", e.getIsSleeperBerthAvailable().get());
            if(e.getNumber().isPresent()) updateOperations.set("number", e.getNumber().get());
            if(e.getLicensePlateState().isPresent()) updateOperations.set("licensePlateState", e.getLicensePlateState().get());
            if(e.getLicensePlateNumber().isPresent()) updateOperations.set("licensePlateNumber", e.getLicensePlateNumber().get());
            if(e.getLicensePlateExpiration().isPresent()) updateOperations.set("licensePlateExpiration", e.getLicensePlateExpiration().get());
            if(e.getNotes().isPresent()) updateOperations.set("notes", e.getNotes().get());
            if(e.getMileageRecords().isPresent()) updateOperations.set("mileageRecords", transformPVectorToList(e.getMileageRecords()));
            
            return updateOperations;
        }

        private CompletionStage<Void> deleteEquipmentSummary(Datastore datastore, String equipmentId) {
            return CompletableFuture.runAsync(() -> {
                        Query<MongoEquipment> equipmentsToDelete = datastore.createQuery(MongoEquipment.class)
                                .field("equipmentId")
                                .equal(equipmentId);
                        datastore.delete(equipmentsToDelete);
                    }
            );
        }
    }
}
