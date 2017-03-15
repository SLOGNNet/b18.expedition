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

        b.setCommandHandler(EquipmentCommand.CreateEquipment.class, (cmd, ctx) ->
                ctx.thenPersist(new EquipmentEvent.EquipmentCreated(entityId(), cmd.getVin(), cmd.getOwnership(),
                                cmd.getType(), cmd.getSubType(), cmd.getOperatingMode(), cmd.getMake(),
                                cmd.getModel(), cmd.getColour(), cmd.getIsSleeperBerthAvailable(),
                                cmd.getNumber(), cmd.getLicensePlateState(),cmd.getLicensePlateNumber(),
                                cmd.getLicensePlateExpiration(), cmd.getNotes(), cmd.getMileageRecords()),

                        evt -> ctx.reply(state())
                )
        );

        b.setCommandHandler(EquipmentCommand.ChangeEquipment.class, (cmd, ctx) ->
                ctx.thenPersist(new EquipmentEvent.EquipmentChanged(entityId(), cmd.getVin(), cmd.getOwnership(),
                                cmd.getType(), cmd.getSubType(), cmd.getOperatingMode(), cmd.getMake(),
                                cmd.getModel(), cmd.getColour(), cmd.getIsSleeperBerthAvailable(),
                                cmd.getNumber(), cmd.getLicensePlateState(),cmd.getLicensePlateNumber(),
                                cmd.getLicensePlateExpiration(), cmd.getNotes(), cmd.getMileageRecords()),

                        evt -> ctx.reply(state())
                )
        );

        b.setCommandHandler(EquipmentCommand.DeleteEquipment.class, (cmd, ctx) ->
                ctx.thenPersist(new EquipmentEvent.EquipmentDeleted(entityId()),
                        evt -> ctx.reply(Done.getInstance())
                        )
        );

        b.setReadOnlyCommandHandler(EquipmentCommand.GetEquipment.class, (cmd, ctx) ->
                ctx.reply(state()));

        b.setEventHandler(EquipmentEvent.EquipmentCreated.class, evt ->
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

        b.setEventHandler(EquipmentEvent.EquipmentChanged.class, evt ->
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

        b.setEventHandler(EquipmentEvent.EquipmentDeleted.class, evt ->
                EquipmentState.builder().id(entityId()).build()
        );

        return b.build();
    }
}
