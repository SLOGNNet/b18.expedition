package com.bridge18.expedition.entities.driver;


import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class DriverEntity extends PersistentEntity<DriverCommand, DriverEvent, DriverState>{
    @Override
    public Behavior initialBehavior(Optional<DriverState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(DriverState.builder().id(entityId()).build()));

        b.setCommandHandler(DriverCommand.CreateDriver.class, (cmd, ctx) ->
                ctx.thenPersist(new DriverEvent.DriverCreated(entityId(), cmd.contactId, cmd.position, cmd.firstName, cmd.middleName, cmd.lastName,
                                cmd.birthDate, cmd.SSN, cmd.paymentOptions, cmd.rate, cmd.contactInfo, cmd.addressId,
                                cmd.addressName, cmd.streetAddress1, cmd.streetAddress2, cmd.city, cmd.addressPhone,
                                cmd.state, cmd.zip, cmd.addressFax, cmd.addressPhoneExtension, cmd.addressFaxExtension,
                                cmd.addressLatitude, cmd.addressLongitude, cmd.licenseNumber, cmd.licenseExpiration,
                                cmd.licenseDateIssued, cmd.licenseStateIssue, cmd.licenseClass, cmd.licenseEndorsements,
                                cmd.licenseRestrictions),

                        evt -> {
                            ctx.reply(state());
                        }));

        b.setCommandHandler(DriverCommand.UpdateDriver.class, (cmd, ctx) ->
                    ctx.thenPersist(new DriverEvent.DriverUpdated(entityId(), cmd.contactId, cmd.position, cmd.firstName, cmd.middleName, cmd.lastName,
                                    cmd.birthDate, cmd.SSN, cmd.paymentOptions, cmd.rate, cmd.contactInfo, cmd.addressId,
                                    cmd.addressName, cmd.streetAddress1, cmd.streetAddress2, cmd.city, cmd.addressPhone,
                                    cmd.state, cmd.zip, cmd.addressFax, cmd.addressPhoneExtension, cmd.addressFaxExtension,
                                    cmd.addressLatitude, cmd.addressLongitude, cmd.licenseNumber, cmd.licenseExpiration,
                                    cmd.licenseDateIssued, cmd.licenseStateIssue, cmd.licenseClass, cmd.licenseEndorsements,
                                    cmd.licenseRestrictions),

                            evt -> {
                                ctx.reply(state());
                            }));


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

        b.setEventHandler(DriverEvent.DriverUpdated.class,
                evt -> DriverState.builder().from(state())
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


        return b.build();
    }
}
