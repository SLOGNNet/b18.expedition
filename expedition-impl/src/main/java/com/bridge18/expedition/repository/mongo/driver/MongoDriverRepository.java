package com.bridge18.expedition.repository.mongo.driver;

import akka.Done;
import com.bridge18.expedition.dto.v1.*;
import com.bridge18.expedition.entities.driver.*;
import com.bridge18.expedition.repository.DriverRepository;
import com.bridge18.readside.mongodb.readside.MongodbReadSide;
import com.google.common.collect.Lists;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.pcollections.PSequence;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.bridge18.expedition.core.CompletionStageUtils.doAll;

@Singleton
public class MongoDriverRepository implements DriverRepository {
    private Datastore datastore;

    @Inject
    public MongoDriverRepository(ReadSide readSide, Datastore datastore) {
        readSide.register(DriverEventProcessor.class);
        this.datastore = datastore;
    }

    @Override
    public CompletionStage<PaginatedSequence<DriverDTO>> getDrivers(int pageNumber, int pageSize) {
        List<Driver> drivers = datastore.createQuery(Driver.class)
                .asList(new FindOptions().skip(pageNumber > 0 ? (pageNumber - 1) * pageSize : 0)
                        .limit(pageSize)
                );
        return CompletableFuture.completedFuture(
                new PaginatedSequence<>(
                        TreePVector.from(
                                drivers.stream()
                                        .map(this::transformDriverToDriverDTO)
                                        .collect(Collectors.toList())
                        ),
                        pageSize,
                        (int) datastore.getCount(datastore.createQuery(Driver.class))
                )


        );
    }

    private DriverDTO transformDriverToDriverDTO(Driver driver) {
        MongoAddress mongoAddress = driver.getAddress();
        AddressDTO addressDTO = mongoAddress != null ?
                new AddressDTO(mongoAddress.getId(),
                        mongoAddress.getName(), mongoAddress.getStreetAddress1(),
                        mongoAddress.getStreetAddress2(), mongoAddress.getCity(),
                        mongoAddress.getPhone(), mongoAddress.getState(),
                        mongoAddress.getZip(), mongoAddress.getFax(),
                        mongoAddress.getPhoneExtension(), mongoAddress.getFaxExtension(),
                        mongoAddress.getLatitude(), mongoAddress.getLongitude()
                ) : null;

        List<ContactInfoDTO> contactInfoDTOList = driver.getContactInfo() != null ?
                Lists.transform(driver.getContactInfo(), mongoContactInfo ->
                        new ContactInfoDTO(mongoContactInfo.getLabel(),
                                mongoContactInfo.getValue(),
                                mongoContactInfo.getType()
                        )
                ) : null;

        MongoLicense mongoLicense = driver.getLicense();
        LicenseDTO licenseDTO = mongoLicense != null ?
                new LicenseDTO(
                        mongoLicense.getNumber(),
                        mongoLicense.getExpiration(), mongoLicense.getDateIssued(),
                        mongoLicense.getStateIssued(), mongoLicense.getLicenseClass(),
                        mongoLicense.getEndorsements(), mongoLicense.getRestrictions()
                ) : null;

        return new DriverDTO(driver.getDriverId(),
                driver.getFirstName(), driver.getMiddleName(),
                driver.getLastName(), contactInfoDTOList,
                driver.getPosition(), addressDTO, driver.getBirthDate(),
                driver.getSsn(), driver.getPaymentOption(),
                driver.getRate(), driver.getType(), licenseDTO
        );
    }


    private static class DriverEventProcessor extends ReadSideProcessor<DriverEvent> {

        private final MongodbReadSide readSide;

        @Inject
        public DriverEventProcessor(MongodbReadSide readSide) {
            this.readSide = readSide;
        }

        @Override
        public ReadSideHandler<DriverEvent> buildHandler() {
            return readSide.<DriverEvent>builder("mongoDriverEventOffset")
                    .setGlobalPrepare(this::globalPrepare)
                    .setPrepare(this::prepareStatements)
                    .setEventHandler(DriverCreated.class,
                            this::insertDriverSummary
                    )
                    .setEventHandler(DriverUpdated.class,
                            this::updateDriverSummary
                    )
                    .setEventHandler(DriverDeleted.class,
                            (datastore, e) -> deleteDriverSummary(datastore, e.getId()))
                    .build();
        }

        private List<MongoContactInfo> transformPVectorToList(Optional<PVector<ContactInfo>> contactInfoPVector) {
            if (!contactInfoPVector.isPresent() || contactInfoPVector.get().isEmpty()) {
                return new ArrayList<>();
            }

            return contactInfoPVector.get().stream()
                    .map(contactInfo -> new MongoContactInfo(
                            contactInfo.getLabel().orElse(null),
                            contactInfo.getValue().orElse(null),
                            contactInfo.getType().orElse(null)
                    ))
                    .collect(Collectors.toList());
        }

        private MongoAddress transformAddressToMongoAddress(Optional<Address> optionalAddress) {
            if (!optionalAddress.isPresent()) {
                return new MongoAddress();
            }
            Address address = optionalAddress.get();
            return new MongoAddress(address.getId().orElse(null), address.getName().orElse(null),
                    address.getStreetAddress1().orElse(null), address.getStreetAddress2().orElse(null),
                    address.getCity().orElse(null), address.getPhone().orElse(null),
                    address.getState().orElse(null), address.getZip().orElse(null),
                    address.getFax().orElse(null), address.getPhoneExtension().orElse(null),
                    address.getFaxExtension().orElse(null), address.getLatitude().orElse(null),
                    address.getLongitude().orElse(null));
        }

        private MongoLicense transformLicenseToMongoLicense(Optional<License> optionalLicense) {
            if (!optionalLicense.isPresent()) {
                return new MongoLicense();
            }
            License license = optionalLicense.get();
            return new MongoLicense(license.getLicenseNumber().orElse(null),
                    license.getLicenseExpiration().orElse(null), license.getLicenseDateIssued().orElse(null),
                    license.getLicenseStateIssued().orElse(null), license.getLicenseClass().orElse(null),
                    license.getLicenseEndorsements().orElse(null), license.getLicenseRestrictions().orElse(null));
        }

        @Override
        public PSequence<AggregateEventTag<DriverEvent>> aggregateTags() {
            return DriverEvent.TAG.allTags();
        }

        private CompletionStage<Done> globalPrepare(Datastore datastore) {
            return doAll(
                    //@TODO: indexing?

                    CompletableFuture.runAsync(() -> {
                        datastore.ensureIndexes(Driver.class);
                    })
            );
        }

        private CompletionStage<Done> prepareStatements(Datastore datastore, AggregateEventTag<DriverEvent> tag) {
            return doAll(
                    //@TODO: indexing?
            );
        }

        private CompletionStage<Void> insertDriverSummary(Datastore datastore,
                                                          DriverCreated e) {

            return CompletableFuture.runAsync(() -> {
                datastore.save(
                        new Driver(e.getId(), e.getFirstName().orElse(null),
                                e.getMiddleName().orElse(null), e.getLastName().orElse(null),
                                transformPVectorToList(e.getContactInfo()), e.getPosition().orElse(null),
                                e.getBirthDate().orElse(null), e.getSsn().orElse(null),
                                e.getPaymentOption().orElse(null), e.getRate().orElse(null),
                                e.getDriverType().orElse(null), transformAddressToMongoAddress(e.getAddress()),
                                transformLicenseToMongoLicense(e.getLicense())
                        )
                );
            });
        }

        private CompletionStage<Void> updateDriverSummary(Datastore datastore,
                                                          DriverUpdated e) {

            return CompletableFuture.runAsync(() -> {
                UpdateOperations<Driver> updateOperations = setNotNullFieldsInUpdateOperations(datastore, e);

                datastore.update(
                        datastore.createQuery(Driver.class).field("driverId").equal(e.getId()),
                        updateOperations
                );
            });
        }

        private UpdateOperations<Driver> setNotNullFieldsInUpdateOperations(Datastore datastore, DriverUpdated e) {
            UpdateOperations updateOperations = datastore.createUpdateOperations(Driver.class);

            updateOperations = e.getFirstName().isPresent() ?
                    updateOperations.set("firstName", e.getFirstName().get()) : updateOperations;
            updateOperations = e.getMiddleName().isPresent() ?
                    updateOperations.set("middleName", e.getMiddleName().get()) : updateOperations;
            updateOperations = e.getLastName().isPresent() ?
                    updateOperations.set("lastName", e.getLastName().get()) : updateOperations;
            updateOperations = e.getContactInfo().isPresent() ?
                    updateOperations.set("contactInfo", transformPVectorToList(e.getContactInfo())) : updateOperations;
            updateOperations = e.getPosition().isPresent() ?
                    updateOperations.set("position", e.getPosition().get()) : updateOperations;
            updateOperations = e.getBirthDate().isPresent() ?
                    updateOperations.set("birthDate", e.getBirthDate().get()) : updateOperations;
            updateOperations = e.getSsn().isPresent() ?
                    updateOperations.set("ssn", e.getSsn().get()) : updateOperations;
            updateOperations = e.getPaymentOption().isPresent() ?
                    updateOperations.set("colour", e.getPaymentOption().get()) : updateOperations;
            updateOperations = e.getRate().isPresent() ?
                    updateOperations.set("rate", e.getRate().get()) : updateOperations;
            updateOperations = e.getDriverType().isPresent() ?
                    updateOperations.set("type", e.getDriverType().get()) : updateOperations;
            updateOperations = e.getAddress().isPresent() ?
                    updateOperations.set("address", transformAddressToMongoAddress(e.getAddress())) : updateOperations;
            updateOperations = e.getLicense().isPresent() ?
                    updateOperations.set("license", transformLicenseToMongoLicense(e.getLicense())) : updateOperations;

            return updateOperations;
        }

        private CompletionStage<Void> deleteDriverSummary(Datastore datastore, String driverId) {
            return CompletableFuture.runAsync(() -> {
                        Query<Driver> driversToDelete = datastore.createQuery(Driver.class)
                                .field("driverId")
                                .equal(driverId);
                        datastore.delete(driversToDelete);
                    }
            );
        }
    }
}