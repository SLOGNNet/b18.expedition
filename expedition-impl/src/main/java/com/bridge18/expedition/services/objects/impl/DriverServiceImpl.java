package com.bridge18.expedition.services.objects.impl;

import akka.Done;
import com.bridge18.expedition.entities.LicenseClass;
import com.bridge18.expedition.entities.PaymentOptions;
import com.bridge18.expedition.entities.driver.*;
import com.bridge18.expedition.services.objects.DriverService;
import javax.inject.Inject;

import com.google.inject.Singleton;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Singleton
public class DriverServiceImpl implements DriverService {
    private final PersistentEntityRegistry persistentEntityRegistry;

    @Inject
    public DriverServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        persistentEntityRegistry.register(DriverEntity.class);
    }

    @Override
    public CompletionStage<DriverState> createDriver(Optional<Integer> contactId, Optional<String> position, Optional<String> firstName, Optional<String> middleName, Optional<String> lastName, Optional<Date> birthDate, Optional<String> SSN, Optional<PaymentOptions> paymentOptions, Optional<Double> rate, Optional<String> contactInfo, Optional<Integer> addressId, Optional<String> addressName, Optional<String> streetAddress1, Optional<String> streetAddress2, Optional<String> city, Optional<String> addressPhone, Optional<String> state, Optional<String> zip, Optional<String> addressFax, Optional<String> addressPhoneExtension, Optional<String> addressFaxExtension, Optional<Double> addressLatitude, Optional<Double> addressLongitude, Optional<Integer> licenseNumber, Optional<Date> licenseExpiration, Optional<Date> licenseDateIssued, Optional<String> licenseStateIssue, Optional<LicenseClass> licenseClass, Optional<String> licenseEndorsements, Optional<String> licenseRestrictions) {
        DriverCommand.CreateDriver cmd = new DriverCommand.CreateDriver(
                contactId, position, firstName, middleName, lastName,
                birthDate, SSN, paymentOptions, rate, contactInfo, addressId,
                addressName, streetAddress1, streetAddress2, city, addressPhone,
                state, zip, addressFax, addressPhoneExtension, addressFaxExtension,
                addressLatitude, addressLongitude, licenseNumber, licenseExpiration,
                licenseDateIssued, licenseStateIssue, licenseClass, licenseEndorsements,
                licenseRestrictions);

        PersistentEntityRef<DriverCommand> ref = persistentEntityRegistry.refFor(DriverEntity.class, UUID.randomUUID().toString());

        return ref.ask(cmd);
    }

    /*@Override
    public CompletionStage<DriverState> changeDriverInformation(String id, Optional<String> firstName, Optional<String> lastName, Optional<Date> birthDate, Optional<String> SSN, Optional<DriverType> driverType, Optional<Date> hireDate, Optional<Date> terminationDate, Optional<EmploymentStatus> status, Optional<PaymentOptions> paymentOptions, Optional<Double> rate, Optional<String> primaryPhone, Optional<PhoneType> primaryPhoneType, Optional<String> altPhone, Optional<PhoneType> altPhoneType, Optional<String> fax, Optional<String> email, Optional<String> streetAddress, Optional<String> secondStreetAddress, Optional<String> city, Optional<String> state, Optional<String> zip, Optional<String> licenseNumber, Optional<Date> licenseExpiration, Optional<Date> licenseDateIssued, Optional<LicenseStateIssue> licenseStateIssue, Optional<LicenseClass> licenseClass, Optional<LicenseEndorsements> licenseEndorsements) {
        DriverCommand.ChangeDriverInformation cmd = new DriverCommand.ChangeDriverInformation(firstName, lastName, birthDate, SSN, driverType, hireDate, terminationDate, status, paymentOptions, rate, primaryPhone, primaryPhoneType, altPhone, altPhoneType, fax, email, streetAddress, secondStreetAddress, city, state, zip, licenseNumber, licenseExpiration, licenseDateIssued, licenseStateIssue, licenseClass, licenseEndorsements);

        PersistentEntityRef<DriverCommand> ref = persistentEntityRegistry.refFor(DriverEntity.class, id);

        return ref.ask(cmd);
    }*/

    @Override
    public CompletionStage<DriverState> getDriverInformation(String id) {
        DriverCommand.GetDriverInformation cmd = new DriverCommand.GetDriverInformation();

        PersistentEntityRef<DriverCommand> ref = persistentEntityRegistry.refFor(DriverEntity.class, id);

        return ref.ask(cmd);
    }
}
