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
                ctx.thenPersist(new DriverEvent.DriverCreated(cmd.contactId, cmd.position, cmd.firstName, cmd.middleName, cmd.lastName,
                                cmd.birthDate, cmd.SSN, cmd.paymentOptions, cmd.rate, cmd.contactInfo, cmd.addressId,
                                cmd.addressName, cmd.streetAddress1, cmd.streetAddress2, cmd.city, cmd.addressPhone,
                                cmd.state, cmd.zip, cmd.addressFax, cmd.addressPhoneExtension, cmd.addressFaxExtension,
                                cmd.addressLatitude, cmd.addressLongitude, cmd.licenseNumber, cmd.licenseExpiration,
                                cmd.licenseDateIssued, cmd.licenseStateIssue, cmd.licenseClass, cmd.licenseEndorsements,
                                cmd.licenseRestrictions),

                        evt -> {
                            ctx.reply(state());
                        }));

        /*b.setCommandHandler(DriverCommand.ChangeDriverInformation.class, (cmd, ctx) ->{
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
                );*/


        b.setReadOnlyCommandHandler(DriverCommand.GetDriverInformation.class, (cmd, ctx) ->
                ctx.reply(state()));


        b.setEventHandler(DriverEvent.DriverCreated.class,
                evt -> DriverState.builder().id(entityId())
                        .contactId(evt.contactId)
                        .position(evt.position)
                        .firstName(evt.firstName)
                        .middleName(evt.middleName)
                        .lastName(evt.lastName)
                        .birthDate(evt.birthDate)
                        .sSN(evt.SSN)
                        .paymentOptions(evt.paymentOptions)
                        .rate(evt.rate)
                        .contactInfo(evt.contactInfo)
                        .addressId(evt.addressId)
                        .addressName(evt.addressName)
                        .streetAddress1(evt.streetAddress1)
                        .streetAddress2(evt.streetAddress2)
                        .city(evt.city)
                        .addressPhone(evt.addressPhone)
                        .state(evt.state)
                        .zip(evt.zip)
                        .addressFax(evt.addressFax)
                        .addressPhoneExtension(evt.addressPhoneExtension)
                        .addressFaxExtension(evt.addressFaxExtension)
                        .addressLatitude(evt.addressLatitude)
                        .addressLongitude(evt.addressLongitude)
                        .licenseNumber(evt.licenseNumber)
                        .licenseExpiration(evt.licenseExpiration)
                        .licenseDateIssued(evt.licenseDateIssued)
                        .licenseStateIssue(evt.licenseStateIssue)
                        .licenseClass(evt.licenseClass)
                        .licenseEndorsements(evt.licenseEndorsements)
                        .licenseRestrictions(evt.licenseRestrictions)
                        .build()
        );

        /*b.setEventHandler(DriverEvent.DriverInformationChanged.class,
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
        );*/


        return b.build();
    }
}
