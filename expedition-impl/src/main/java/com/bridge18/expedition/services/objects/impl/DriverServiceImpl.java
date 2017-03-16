package com.bridge18.expedition.services.objects.impl;

import com.bridge18.expedition.entities.driver.LicenseClass;
import com.bridge18.expedition.entities.driver.PaymentOptions;
import com.bridge18.expedition.entities.driver.*;
import com.bridge18.expedition.services.objects.DriverService;
import javax.inject.Inject;

import com.google.inject.Singleton;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import org.pcollections.PVector;

import java.util.Date;
import java.util.List;
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
    public CompletionStage<DriverState> createDriver(Optional<Integer> contactId, Optional<String> position, Optional<String> firstName, Optional<String> middleName, Optional<String> lastName, Optional<Date> birthDate, Optional<String> SSN, Optional<PaymentOptions> paymentOptions, Optional<Double> rate, Optional<PVector<ContactInfo>> contactInfo, Optional<Integer> addressId, Optional<String> addressName, Optional<String> streetAddress1, Optional<String> streetAddress2, Optional<String> city, Optional<String> addressPhone, Optional<String> state, Optional<String> zip, Optional<String> addressFax, Optional<String> addressPhoneExtension, Optional<String> addressFaxExtension, Optional<Double> addressLatitude, Optional<Double> addressLongitude, Optional<Integer> licenseNumber, Optional<Date> licenseExpiration, Optional<Date> licenseDateIssued, Optional<String> licenseStateIssue, Optional<LicenseClass> licenseClass, Optional<String> licenseEndorsements, Optional<String> licenseRestrictions) {
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

    @Override
    public CompletionStage<DriverState> changeDriverInformation(String id, Optional<Integer> contactId, Optional<String> position, Optional<String> firstName, Optional<String> middleName, Optional<String> lastName, Optional<Date> birthDate, Optional<String> SSN, Optional<PaymentOptions> paymentOptions, Optional<Double> rate, Optional<PVector<ContactInfo>> contactInfo, Optional<Integer> addressId, Optional<String> addressName, Optional<String> streetAddress1, Optional<String> streetAddress2, Optional<String> city, Optional<String> addressPhone, Optional<String> state, Optional<String> zip, Optional<String> addressFax, Optional<String> addressPhoneExtension, Optional<String> addressFaxExtension, Optional<Double> addressLatitude, Optional<Double> addressLongitude, Optional<Integer> licenseNumber, Optional<Date> licenseExpiration, Optional<Date> licenseDateIssued, Optional<String> licenseStateIssue, Optional<LicenseClass> licenseClass, Optional<String> licenseEndorsements, Optional<String> licenseRestrictions){
        DriverCommand.UpdateDriver cmd = new DriverCommand.UpdateDriver(
                contactId, position, firstName, middleName, lastName,
                birthDate, SSN, paymentOptions, rate, contactInfo, addressId,
                addressName, streetAddress1, streetAddress2, city, addressPhone,
                state, zip, addressFax, addressPhoneExtension, addressFaxExtension,
                addressLatitude, addressLongitude, licenseNumber, licenseExpiration,
                licenseDateIssued, licenseStateIssue, licenseClass, licenseEndorsements,
                licenseRestrictions);

        PersistentEntityRef<DriverCommand> ref = persistentEntityRegistry.refFor(DriverEntity.class, id);

        return ref.ask(cmd);
    }

    @Override
    public CompletionStage<DriverState> getDriverInformation(String id) {
        DriverCommand.GetDriverInformation cmd = new DriverCommand.GetDriverInformation();

        PersistentEntityRef<DriverCommand> ref = persistentEntityRegistry.refFor(DriverEntity.class, id);

        return ref.ask(cmd);
    }
}
