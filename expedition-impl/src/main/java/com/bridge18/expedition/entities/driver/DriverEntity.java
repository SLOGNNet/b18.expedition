package com.bridge18.expedition.entities.driver;


import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class DriverEntity extends PersistentEntity<DriverCommand, DriverEvent, DriverState>{
    @Override
    public Behavior initialBehavior(Optional<DriverState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(DriverState.builder().id(entityId()).build()));

        b.setCommandHandler(CreateDriver.class, (cmd, ctx) ->
                ctx.thenPersist(DriverCreated.builder()
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
                                .license(cmd.getLicense())
                                .build(),

                        evt -> {
                            ctx.reply(state());
                        }));

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
                                .license(cmd.getLicense())
                                .build(),

                        evt -> {
                            ctx.reply(state());
                        }));


        b.setReadOnlyCommandHandler(GetDriverInformation.class, (cmd, ctx) ->
                ctx.reply(state()));


        b.setEventHandler(DriverCreated.class,
                evt -> DriverState.builder().id(entityId())
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
                        .license(evt.getLicense())
                        .build()
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
                        .license(evt.getLicense())
                        .build()
        );


        return b.build();
    }
}
