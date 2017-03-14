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
                ctx.thenPersist(new EquipmentEvent.EquipmentCreated(entityId(), cmd.vin, cmd.ownership,
                        cmd.type, cmd.subType, cmd.operatingMode, cmd.make, cmd.model, cmd.colour,
                        cmd.isSleeperBerthAvailable, cmd.number, cmd.licensePlateState,
                        cmd.licensePlateNumber, cmd.licensePlateExpiration, cmd.notes, cmd.miles, cmd.takenAt),

                        evt -> ctx.reply(state())
                )
        );

        b.setCommandHandler(EquipmentCommand.ChangeEquipment.class, (cmd, ctx) ->
                ctx.thenPersist(new EquipmentEvent.EquipmentChanged(entityId(), cmd.vin, cmd.ownership,
                                cmd.type, cmd.subType, cmd.operatingMode, cmd.make, cmd.model, cmd.colour,
                                cmd.isSleeperBerthAvailable, cmd.number, cmd.licensePlateState,
                                cmd.licensePlateNumber, cmd.licensePlateExpiration, cmd.notes, cmd.miles, cmd.takenAt),

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
                .vin(evt.vin)
                .ownership(evt.ownership)
                .type(evt.type)
                .subType(evt.subType)
                .operatingMode(evt.operatingMode)
                .make(evt.make)
                .model(evt.model)
                .colour(evt.colour)
                .isSleeperBerthAvailable(evt.isSleeperBerthAvailable)
                .number(evt.number)
                .licensePlateState(evt.licensePlateState)
                .licensePlateNumber(evt.licensePlateNumber)
                .licensePlateExpiration(evt.licensePlateExpiration)
                .notes(evt.notes)
                .miles(evt.miles)
                .takenAt(evt.takenAt)
                .build()
        );

        b.setEventHandler(EquipmentEvent.EquipmentChanged.class, evt ->
                EquipmentState.builder().id(entityId())
                        .vin(evt.vin)
                        .ownership(evt.ownership)
                        .type(evt.type)
                        .subType(evt.subType)
                        .operatingMode(evt.operatingMode)
                        .make(evt.make)
                        .model(evt.model)
                        .colour(evt.colour)
                        .isSleeperBerthAvailable(evt.isSleeperBerthAvailable)
                        .number(evt.number)
                        .licensePlateState(evt.licensePlateState)
                        .licensePlateNumber(evt.licensePlateNumber)
                        .licensePlateExpiration(evt.licensePlateExpiration)
                        .notes(evt.notes)
                        .miles(evt.miles)
                        .takenAt(evt.takenAt)
                        .build()
        );

        b.setEventHandler(EquipmentEvent.EquipmentDeleted.class, evt ->
                EquipmentState.builder().id(entityId()).build()
        );

        return b.build();
    }
}
