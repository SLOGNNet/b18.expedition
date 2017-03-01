package com.bridge18.expedition.entities.driver;


import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class DriverEntity extends PersistentEntity<DriverCommand, DriverEvent, DriverState>{
    @Override
    public Behavior initialBehavior(Optional<DriverState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(DriverState.builder().id(entityId()).build()));

        b.setCommandHandler(DriverCommand.CreateDriver.class, (cmd, ctx) ->
                ctx.thenPersist(new DriverEvent.DriverCreated(cmd.firstName, cmd.lastName,
                        cmd.birthDate, cmd.SSN, cmd.driverType, cmd.hireDate,
                        cmd.terminationDate, cmd.status, cmd.paymentOptions, cmd.rate,
                        cmd.primaryPhone, cmd.primaryPhoneType, cmd.altPhone, cmd.altPhoneType,
                        cmd.fax, cmd.email, cmd.streetAddress, cmd.secondStreetAddress, cmd.city,
                        cmd.state, cmd.zip, cmd.licenseNumber, cmd.licenseExpiration, cmd.licenseDateIssued,
                        cmd.licenseStateIssue, cmd.licenseClass, cmd.licenseEndorsements),

                        evt -> {
                            ctx.reply(state());
                        }));

        b.setCommandHandler(DriverCommand.ChangeDriverInformation.class, (cmd, ctx) ->{
                    if(!state().getIsActive().orElse(false)){
                        ctx.invalidCommand("This driver is disabled.");
                        return ctx.done();
                    }
                    return ctx.thenPersist(new DriverEvent.DriverInformationChanged(cmd.firstName, cmd.lastName,
                                    cmd.birthDate, cmd.SSN, cmd.driverType, cmd.hireDate,
                                    cmd.terminationDate, cmd.status, cmd.paymentOptions, cmd.rate,
                                    cmd.primaryPhone, cmd.primaryPhoneType, cmd.altPhone, cmd.altPhoneType,
                                    cmd.fax, cmd.email, cmd.streetAddress, cmd.secondStreetAddress, cmd.city,
                                    cmd.state, cmd.zip, cmd.licenseNumber, cmd.licenseExpiration, cmd.licenseDateIssued,
                                    cmd.licenseStateIssue, cmd.licenseClass, cmd.licenseEndorsements),

                            evt -> {
                                ctx.reply(state());
                            });
                }
                );

        b.setCommandHandler(DriverCommand.DisableDriver.class, (cmd, ctx) ->
                ctx.thenPersist(new DriverEvent.DriverDisabled(cmd.reason),

                        evt -> {
                            ctx.reply(Done.getInstance());
                        }));

        b.setReadOnlyCommandHandler(DriverCommand.GetDriverInformation.class, (cmd, ctx) ->
                ctx.reply(state()));


        b.setEventHandler(DriverEvent.DriverCreated.class,
                evt -> DriverState.builder().id(entityId())
                        .isActive(true)
                        .firstName(evt.firstName)
                        .lastName(evt.lastName)
                        .birthDate(evt.birthDate)
                        .sSN(evt.SSN)
                        .driverType(evt.driverType)
                        .hireDate(evt.hireDate)
                        .terminationDate(evt.terminationDate)
                        .status(evt.status)
                        .paymentOptions(evt.paymentOptions)
                        .altPhone(evt.altPhone)
                        .altPhoneType(evt.altPhoneType)
                        .primaryPhone(evt.primaryPhone)
                        .primaryPhoneType(evt.primaryPhoneType)
                        .fax(evt.fax)
                        .email(evt.email)
                        .streetAddress(evt.streetAddress)
                        .secondStreetAddress(evt.secondStreetAddress)
                        .city(evt.city)
                        .state(evt.state)
                        .zip(evt.zip)
                        .licenseNumber(evt.licenseNumber)
                        .licenseExpiration(evt.licenseExpiration)
                        .licenseDateIssued(evt.licenseDateIssued)
                        .licenseStateIssue(evt.licenseStateIssue)
                        .licenseClass(evt.licenseClass)
                        .licenseEndorsements(evt.licenseEndorsements)
                        .build()
        );

        b.setEventHandler(DriverEvent.DriverInformationChanged.class,
                evt -> DriverState.builder().from(state())
                        .firstName(evt.firstName)
                        .lastName(evt.lastName)
                        .birthDate(evt.birthDate)
                        .sSN(evt.SSN)
                        .driverType(evt.driverType)
                        .hireDate(evt.hireDate)
                        .terminationDate(evt.terminationDate)
                        .status(evt.status)
                        .paymentOptions(evt.paymentOptions)
                        .altPhone(evt.altPhone)
                        .altPhoneType(evt.altPhoneType)
                        .primaryPhone(evt.primaryPhone)
                        .primaryPhoneType(evt.primaryPhoneType)
                        .fax(evt.fax)
                        .email(evt.email)
                        .streetAddress(evt.streetAddress)
                        .secondStreetAddress(evt.secondStreetAddress)
                        .city(evt.city)
                        .state(evt.state)
                        .zip(evt.zip)
                        .licenseNumber(evt.licenseNumber)
                        .licenseExpiration(evt.licenseExpiration)
                        .licenseDateIssued(evt.licenseDateIssued)
                        .licenseStateIssue(evt.licenseStateIssue)
                        .licenseClass(evt.licenseClass)
                        .licenseEndorsements(evt.licenseEndorsements)
                        .build()
        );

        b.setEventHandler(DriverEvent.DriverDisabled.class,
                evt -> DriverState.builder().from(state())
                        .isActive(false)
                        .build()
        );

        return b.build();
    }
}
