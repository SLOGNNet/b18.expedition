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
                                cmd.getAddressId(), cmd.getAddressName(), cmd.getStreetAddress1(), cmd.getStreetAddress2(),
                                cmd.getCity(), cmd.getAddressPhone(), cmd.getState(), cmd.getZip(), cmd.getAddressFax(),
                                cmd.getAddressPhoneExtension(), cmd.getAddressFaxExtension(), cmd.getAddressLatitude(),
                                cmd.getAddressLongitude(), cmd.getLicenseNumber(), cmd.getLicenseExpiration(), cmd.getLicenseDateIssued(),
                                cmd.getLicenseStateIssue(), cmd.getLicenseClass(), cmd.getLicenseEndorsements(), cmd.getLicenseRestrictions()),

                        evt -> {
                            ctx.reply(state());
                        }));

        b.setCommandHandler(DriverCommand.UpdateDriver.class, (cmd, ctx) ->
                    ctx.thenPersist(new DriverEvent.DriverUpdated(entityId(), cmd.getContactId(), cmd.getPosition(),
                                    cmd.getFirstName(), cmd.getMiddleName(), cmd.getLastName(), cmd.getBirthDate(),
                                    cmd.getSSN(), cmd.getPaymentOptions(), cmd.getRate(), cmd.getContactInfo(),
                                    cmd.getAddressId(), cmd.getAddressName(), cmd.getStreetAddress1(), cmd.getStreetAddress2(),
                                    cmd.getCity(), cmd.getAddressPhone(), cmd.getState(), cmd.getZip(), cmd.getAddressFax(),
                                    cmd.getAddressPhoneExtension(), cmd.getAddressFaxExtension(), cmd.getAddressLatitude(),
                                    cmd.getAddressLongitude(), cmd.getLicenseNumber(), cmd.getLicenseExpiration(), cmd.getLicenseDateIssued(),
                                    cmd.getLicenseStateIssue(), cmd.getLicenseClass(), cmd.getLicenseEndorsements(), cmd.getLicenseRestrictions()),

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
                        .addressId(evt.getAddressId())
                        .addressName(evt.getAddressName())
                        .streetAddress1(evt.getStreetAddress1())
                        .streetAddress2(evt.getStreetAddress2())
                        .city(evt.getCity())
                        .addressPhone(evt.getAddressPhone())
                        .state(evt.getState())
                        .zip(evt.getZip())
                        .addressFax(evt.getAddressFax())
                        .addressPhoneExtension(evt.getAddressPhoneExtension())
                        .addressFaxExtension(evt.getAddressFaxExtension())
                        .addressLatitude(evt.getRate())
                        .addressLongitude(evt.getAddressLongitude())
                        .licenseNumber(evt.getLicenseNumber())
                        .licenseExpiration(evt.getLicenseExpiration())
                        .licenseDateIssued(evt.getLicenseDateIssued())
                        .licenseStateIssue(evt.getLicenseStateIssue())
                        .licenseClass(evt.getLicenseClass())
                        .licenseEndorsements(evt.getLicenseEndorsements())
                        .licenseRestrictions(evt.getLicenseRestrictions())
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
                        .addressId(evt.getAddressId())
                        .addressName(evt.getAddressName())
                        .streetAddress1(evt.getStreetAddress1())
                        .streetAddress2(evt.getStreetAddress2())
                        .city(evt.getCity())
                        .addressPhone(evt.getAddressPhone())
                        .state(evt.getState())
                        .zip(evt.getZip())
                        .addressFax(evt.getAddressFax())
                        .addressPhoneExtension(evt.getAddressPhoneExtension())
                        .addressFaxExtension(evt.getAddressFaxExtension())
                        .addressLatitude(evt.getRate())
                        .addressLongitude(evt.getAddressLongitude())
                        .licenseNumber(evt.getLicenseNumber())
                        .licenseExpiration(evt.getLicenseExpiration())
                        .licenseDateIssued(evt.getLicenseDateIssued())
                        .licenseStateIssue(evt.getLicenseStateIssue())
                        .licenseClass(evt.getLicenseClass())
                        .licenseEndorsements(evt.getLicenseEndorsements())
                        .licenseRestrictions(evt.getLicenseRestrictions())
                        .build()
        );


        return b.build();
    }
}
