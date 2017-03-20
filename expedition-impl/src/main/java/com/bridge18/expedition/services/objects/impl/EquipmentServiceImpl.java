package com.bridge18.expedition.services.objects.impl;

import akka.Done;
import com.bridge18.expedition.entities.equipment.*;
import com.bridge18.expedition.services.objects.EquipmentService;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import org.pcollections.PVector;

import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class EquipmentServiceImpl implements EquipmentService {
    private PersistentEntityRegistry persistentEntityRegistry;

    @Inject
    public EquipmentServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        persistentEntityRegistry.register(EquipmentEntity.class);
    }

    @Override
    public CompletionStage<EquipmentState> createEquipment(Optional<String> vin, Optional<Ownership> ownership, Optional<EquipmentType> type, Optional<EquipmentSubType> subType, Optional<OperatingMode> operatingMode, Optional<String> make, Optional<String> model, Optional<String> colour, Optional<Boolean> isSleeperBerthAvailable, Optional<String> number, Optional<String> licensePlateState, Optional<String> licensePlateNumber, Optional<Date> licensePlateExpiration, Optional<String> notes, Optional<PVector<MileageRecord>> mileageRecords) {
        if(!subTypeMatchesToType(subType.get(), type.get())){
            throw new WebServiceException("Subtype doesn't match to equipment type.");
        }
        CreateEquipment cmd = CreateEquipment.builder()
                .vin(vin)
                .ownership(ownership)
                .type(type)
                .subType(subType)
                .operatingMode(operatingMode)
                .make(make)
                .model(model)
                .colour(colour)
                .isSleeperBerthAvailable(isSleeperBerthAvailable)
                .number(number)
                .licensePlateState(licensePlateState)
                .licensePlateNumber(licensePlateNumber)
                .licensePlateExpiration(licensePlateExpiration)
                .notes(notes)
                .mileageRecords(mileageRecords)
                .build();

        PersistentEntityRef<EquipmentCommand> ref = persistentEntityRegistry.refFor(EquipmentEntity.class,
                UUID.randomUUID().toString());

        return ref.ask(cmd);
    }

    @Override
    public CompletionStage<EquipmentState> changeEquipment(String id, Optional<String> vin, Optional<Ownership> ownership, Optional<EquipmentType> type, Optional<EquipmentSubType> subType, Optional<OperatingMode> operatingMode, Optional<String> make, Optional<String> model, Optional<String> colour, Optional<Boolean> isSleeperBerthAvailable, Optional<String> number, Optional<String> licensePlateState, Optional<String> licensePlateNumber, Optional<Date> licensePlateExpiration, Optional<String> notes, Optional<PVector<MileageRecord>> mileageRecords) {
        if(!subTypeMatchesToType(subType.get(), type.get())){
            throw new WebServiceException("Subtype doesn't match to equipment type.");
        }

        UpdateEquipment cmd = UpdateEquipment.builder()
                .vin(vin)
                .ownership(ownership)
                .type(type)
                .subType(subType)
                .operatingMode(operatingMode)
                .make(make)
                .model(model)
                .colour(colour)
                .isSleeperBerthAvailable(isSleeperBerthAvailable)
                .number(number)
                .licensePlateState(licensePlateState)
                .licensePlateNumber(licensePlateNumber)
                .licensePlateExpiration(licensePlateExpiration)
                .notes(notes)
                .mileageRecords(mileageRecords)
                .build();

        PersistentEntityRef<EquipmentCommand> ref = persistentEntityRegistry.refFor(EquipmentEntity.class, id);

        return ref.ask(cmd);
    }

    @Override
    public CompletionStage<Done> deleteEquipment(String id) {
        DeleteEquipment cmd = DeleteEquipment.builder().build();

        PersistentEntityRef<EquipmentCommand> ref = persistentEntityRegistry.refFor(EquipmentEntity.class, id);

        return ref.ask(cmd);
    }

    @Override
    public CompletionStage<EquipmentState> getEquipment(String id) {
        GetEquipment cmd = GetEquipment.builder().build();

        PersistentEntityRef<EquipmentCommand> ref = persistentEntityRegistry.refFor(EquipmentEntity.class, id);

        return ref.ask(cmd);
    }

    private Boolean subTypeMatchesToType(EquipmentSubType equipmentSubType, EquipmentType equipmentType){
        return equipmentSubType.getParentType().equals(equipmentType);
    }
}
