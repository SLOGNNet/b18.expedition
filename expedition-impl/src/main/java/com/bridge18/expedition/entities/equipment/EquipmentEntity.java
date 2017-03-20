package com.bridge18.expedition.entities.equipment;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class EquipmentEntity extends PersistentEntity<EquipmentCommand, EquipmentEvent, EquipmentState> {
    @Override
    public Behavior initialBehavior(Optional<EquipmentState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(EquipmentState.builder().id(entityId()).build())
        );

        b.setCommandHandler(CreateEquipment.class, (cmd, ctx) ->
                ctx.thenPersist(EquipmentCreated.builder().id(entityId())
                        .vin(cmd.getVin())
                        .ownership(cmd.getOwnership())
                        .type(cmd.getType())
                        .subType(cmd.getSubType())
                        .operatingMode(cmd.getOperatingMode())
                        .make(cmd.getMake())
                        .model(cmd.getModel())
                        .colour(cmd.getColour())
                        .isSleeperBerthAvailable(cmd.getIsSleeperBerthAvailable())
                        .number(cmd.getNumber())
                        .licensePlateState(cmd.getLicensePlateState())
                        .licensePlateNumber(cmd.getLicensePlateNumber())
                        .licensePlateExpiration(cmd.getLicensePlateExpiration())
                        .notes(cmd.getNotes())
                        .mileageRecords(cmd.getMileageRecords())
                        .build(),

                        evt -> ctx.reply(state())
                )
        );

        b.setCommandHandler(UpdateEquipment.class, (cmd, ctx) ->
                ctx.thenPersist(EquipmentUpdated.builder().id(entityId())
                                .vin(cmd.getVin())
                                .ownership(cmd.getOwnership())
                                .type(cmd.getType())
                                .subType(cmd.getSubType())
                                .operatingMode(cmd.getOperatingMode())
                                .make(cmd.getMake())
                                .model(cmd.getModel())
                                .colour(cmd.getColour())
                                .isSleeperBerthAvailable(cmd.getIsSleeperBerthAvailable())
                                .number(cmd.getNumber())
                                .licensePlateState(cmd.getLicensePlateState())
                                .licensePlateNumber(cmd.getLicensePlateNumber())
                                .licensePlateExpiration(cmd.getLicensePlateExpiration())
                                .notes(cmd.getNotes())
                                .mileageRecords(cmd.getMileageRecords())
                                .build(),

                        evt -> ctx.reply(state())
                )
        );

        b.setCommandHandler(DeleteEquipment.class, (cmd, ctx) ->
                ctx.thenPersist(EquipmentDeleted.builder().id(entityId()).build(),
                        evt -> ctx.reply(Done.getInstance())
                        )
        );

        b.setReadOnlyCommandHandler(GetEquipment.class, (cmd, ctx) ->
                ctx.reply(state()));

        b.setEventHandler(EquipmentCreated.class, evt ->
                EquipmentState.builder().id(entityId())
                .vin(evt.getVin())
                .ownership(evt.getOwnership())
                .type(evt.getType())
                .subType(evt.getSubType())
                .operatingMode(evt.getOperatingMode())
                .make(evt.getMake())
                .model(evt.getModel())
                .colour(evt.getColour())
                .isSleeperBerthAvailable(evt.getIsSleeperBerthAvailable())
                .number(evt.getNumber())
                .licensePlateState(evt.getLicensePlateState())
                .licensePlateNumber(evt.getLicensePlateNumber())
                .licensePlateExpiration(evt.getLicensePlateExpiration())
                .notes(evt.getNotes())
                .mileageRecords(evt.getMileageRecords())
                .build()
        );

        b.setEventHandler(EquipmentUpdated.class, evt ->
                EquipmentState.builder().id(entityId())
                        .vin(evt.getVin())
                        .ownership(evt.getOwnership())
                        .type(evt.getType())
                        .subType(evt.getSubType())
                        .operatingMode(evt.getOperatingMode())
                        .make(evt.getMake())
                        .model(evt.getModel())
                        .colour(evt.getColour())
                        .isSleeperBerthAvailable(evt.getIsSleeperBerthAvailable())
                        .number(evt.getNumber())
                        .licensePlateState(evt.getLicensePlateState())
                        .licensePlateNumber(evt.getLicensePlateNumber())
                        .licensePlateExpiration(evt.getLicensePlateExpiration())
                        .notes(evt.getNotes())
                        .mileageRecords(evt.getMileageRecords())
                        .build()
        );

        b.setEventHandler(EquipmentDeleted.class, evt ->
                EquipmentState.builder().id(entityId()).build()
        );

        return b.build();
    }
}
