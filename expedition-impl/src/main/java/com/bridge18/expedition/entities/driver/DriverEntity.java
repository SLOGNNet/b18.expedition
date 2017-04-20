package com.bridge18.expedition.entities.driver;


import akka.Done;
import akka.japi.Effect;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class DriverEntity extends PersistentEntity<DriverCommand, DriverEvent, DriverState> {
    @Override
    public Behavior initialBehavior(Optional<DriverState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(DriverState.builder().id(entityId()).build()));

        b.setCommandHandler(CreateDriver.class, (cmd, ctx) ->
                ctx.thenPersistAll(
                        () -> ctx.reply(state()),
                        DriverCreated.builder()
                                .id(entityId())
                                .position(cmd.getPosition())
                                .firstName(cmd.getFirstName())
                                .middleName(cmd.getMiddleName())
                                .lastName(cmd.getLastName())
                                .birthDate(cmd.getBirthDate())
                                .ssn(cmd.getSsn())
                                .paymentOption(cmd.getPaymentOption())
                                .rate(cmd.getRate())
                                .contactInfo(cmd.getContactInfo())
                                .address(cmd.getAddress())
                                .driverType(cmd.getDriverType())
                                .license(cmd.getLicense())
                                .build(),
                        DriverUpdated.builder()
                                .id(entityId())
                                .position(cmd.getPosition())
                                .firstName(cmd.getFirstName())
                                .middleName(cmd.getMiddleName())
                                .lastName(cmd.getLastName())
                                .birthDate(cmd.getBirthDate())
                                .ssn(cmd.getSsn())
                                .paymentOption(cmd.getPaymentOption())
                                .rate(cmd.getRate())
                                .contactInfo(cmd.getContactInfo())
                                .address(cmd.getAddress())
                                .driverType(cmd.getDriverType())
                                .license(cmd.getLicense())
                                .build()
                        ));

        b.setEventHandlerChangingBehavior(
                DriverCreated.class,
                driverCreated -> created(state())
        );


        return b.build();
    }

    private Behavior created(DriverState state) {
        BehaviorBuilder b = newBehaviorBuilder(state);

        b.setCommandHandler(UpdateDriver.class, (cmd, ctx) ->
                ctx.thenPersist(DriverUpdated.builder()
                                .id(entityId())
                                .position(cmd.getPosition())
                                .firstName(cmd.getFirstName())
                                .middleName(cmd.getMiddleName())
                                .lastName(cmd.getLastName())
                                .birthDate(cmd.getBirthDate())
                                .ssn(cmd.getSsn())
                                .paymentOption(cmd.getPaymentOption())
                                .rate(cmd.getRate())
                                .contactInfo(cmd.getContactInfo())
                                .address(cmd.getAddress())
                                .driverType(cmd.getDriverType())
                                .license(cmd.getLicense())
                                .build(),

                        evt -> {
                            ctx.reply(state());
                        }));


        b.setReadOnlyCommandHandler(GetDriverInformation.class, (cmd, ctx) ->
                ctx.reply(state())
        );

        b.setCommandHandler(DeleteDriver.class, (cmd, ctx) ->
                ctx.thenPersist(DriverDeleted.builder().id(entityId()).build(),
                        evt -> ctx.reply(Done.getInstance())
                )
        );

        b.setEventHandler(DriverUpdated.class,
                evt -> DriverState.builder().from(state())
                        .position(evt.getPosition())
                        .firstName(evt.getFirstName())
                        .middleName(evt.getMiddleName())
                        .lastName(evt.getLastName())
                        .birthDate(evt.getBirthDate())
                        .ssn(evt.getSsn())
                        .paymentOption(evt.getPaymentOption())
                        .rate(evt.getRate())
                        .contactInfo(evt.getContactInfo())
                        .address(evt.getAddress())
                        .driverType(evt.getDriverType())
                        .license(evt.getLicense())
                        .build()
        );

        b.setEventHandlerChangingBehavior(
                DriverDeleted.class,
                driverDeleted -> deleted(state())
        );

        return b.build();
    }

    private Behavior deleted(DriverState state) {
        BehaviorBuilder b = newBehaviorBuilder(state);

        b.setReadOnlyCommandHandler(DeleteDriver.class, this::alreadyDone);

        return b.build();
    }

    private void alreadyDone(Object command, ReadOnlyCommandContext<Done> ctx) {
        ctx.reply(Done.getInstance());
    }
}
