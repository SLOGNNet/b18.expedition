package com.bridge18.expedition.entities.driver;


import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class DriverEntity extends PersistentEntity<DriverCommand, DriverEvent, DriverState>{
    @Override
    public Behavior initialBehavior(Optional<DriverState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(DriverState.builder().id(entityId()).build()));

        b.setCommandHandler(DriverCommand.CreateDriver.class, (cmd, ctx) ->
                ctx.thenPersist(new DriverEvent.DriverCreated(entityId(), cmd.getContactId(), cmd.getPosition(),
                                cmd.getFirstName(), cmd.getMiddleName(), cmd.getLastName(), cmd.getBirthDate(),
                                cmd.getSSN(), cmd.getPaymentOptions(), cmd.getRate(), cmd.getContactInfo(),
                                cmd.getAddress(), cmd.getLicense()),

                        evt -> {
                            ctx.reply(state());
                        }));

        b.setCommandHandler(DriverCommand.UpdateDriver.class, (cmd, ctx) ->
                    ctx.thenPersist(new DriverEvent.DriverUpdated(entityId(), cmd.getContactId(), cmd.getPosition(),
                                    cmd.getFirstName(), cmd.getMiddleName(), cmd.getLastName(), cmd.getBirthDate(),
                                    cmd.getSSN(), cmd.getPaymentOptions(), cmd.getRate(), cmd.getContactInfo(),
                                    cmd.getAddress(), cmd.getLicense()),

                            evt -> {
                                ctx.reply(state());
                            }));


        b.setReadOnlyCommandHandler(DriverCommand.GetDriverInformation.class, (cmd, ctx) ->
                ctx.reply(state()));


        b.setEventHandler(DriverEvent.DriverCreated.class,
                evt -> DriverState.builder().id(entityId())
                        .contactId(evt.getContactId())
                        .position(evt.getPosition())
                        .firstName(evt.getFirstName())
                        .middleName(evt.getMiddleName())
                        .lastName(evt.getLastName())
                        .birthDate(evt.getBirthDate())
                        .sSN(evt.getSSN())
                        .paymentOptions(evt.getPaymentOptions())
                        .rate(evt.getRate())
                        .contactInfo(evt.getContactInfo())
                        .address(evt.getAddress())
                        .license(evt.getLicense())
                        .build()
        );

        b.setEventHandler(DriverEvent.DriverUpdated.class,
                evt -> DriverState.builder().from(state())
                        .contactId(evt.getContactId())
                        .position(evt.getPosition())
                        .firstName(evt.getFirstName())
                        .middleName(evt.getMiddleName())
                        .lastName(evt.getLastName())
                        .birthDate(evt.getBirthDate())
                        .sSN(evt.getSSN())
                        .paymentOptions(evt.getPaymentOptions())
                        .rate(evt.getRate())
                        .contactInfo(evt.getContactInfo())
                        .address(evt.getAddress())
                        .license(evt.getLicense())
                        .build()
        );


        return b.build();
    }
}
