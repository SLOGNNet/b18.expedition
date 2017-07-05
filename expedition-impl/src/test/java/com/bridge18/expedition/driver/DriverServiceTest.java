package com.bridge18.expedition.driver;

import com.bridge18.expedition.entities.driver.*;
import com.bridge18.expedition.services.objects.DriverService;
import com.bridge18.expedition.services.objects.impl.DriverServiceImpl;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DriverServiceTest {
    private PersistentEntityRegistry persistentEntityRegistry;

    private DriverService driverService;

    @Before
    public void before() {
        persistentEntityRegistry = Mockito.mock(PersistentEntityRegistry.class);

        Mockito.doNothing().doThrow(Throwable.class).when(persistentEntityRegistry).register(DriverEntity.class);

        driverService = new DriverServiceImpl(persistentEntityRegistry);
    }

    @Test
    public void test() throws InterruptedException, ExecutionException, TimeoutException {
        PersistentEntityRef ref = Mockito.mock(PersistentEntityRef.class);

        Mockito.when(persistentEntityRegistry.refFor(Mockito.any(), Mockito.any())).thenReturn(ref);


        Date birthDate = new Date();
        Date licenseExpiration = new Date();
        Date licenseDateIssued = new Date();
        Double rate = 0.324;
        Address driverAddress = Address.builder()
                .id(1)
                .name("addressName-1")
                .streetAddress1("streetAddress1-1")
                .streetAddress2("streetAddress2-1")
                .city("city-1")
                .phone("addressPhone-1")
                .state("state-1")
                .zip("zip-1")
                .fax("addressFax-1")
                .phoneExtension("addressPhoneExtension-1")
                .faxExtension("addressFaxExtension-1")
                .latitude(1)
                .longitude(1)
                .build();

        License driverLicense = License.builder()
                .licenseNumber("licenseNumber-1")
                .licenseExpiration(licenseExpiration)
                .licenseDateIssued(licenseDateIssued)
                .licenseStateIssued("licenseStateIssued-1")
                .licenseClass(LicenseClass.NONE)
                .licenseEndorsements("licenseEndorsements-1")
                .licenseRestrictions("licenseRestrictions-1")
                .build();

        PVector<ContactInfo> contactInfos = TreePVector.from(
                Arrays.asList(
                        ContactInfo.builder().label("label-1").value("value-1").type(ContactInfoType.NONE).build(),
                        ContactInfo.builder().label("label-2").value("value-2").type(ContactInfoType.NONE).build(),
                        ContactInfo.builder().label("label-3").value("value-3").type(ContactInfoType.NONE).build()
                )
        );
        Mockito.when(ref.ask(Mockito.any(CreateDriver.class))).thenReturn(CompletableFuture.completedFuture(
                DriverState.builder()
                        .id("1")
                        .position("position-1")
                        .firstName("firstName-1")
                        .middleName("middleName-1")
                        .lastName("lastName-1")
                        .contactInfo(contactInfos)
                        .birthDate(birthDate)
                        .ssn("ssn-1")
                        .paymentOption(PaymentOptions.NONE)
                        .rate(rate)
                        .address(driverAddress)
                        .license(driverLicense)
                        .driverType(DriverTypes.NONE)
                        .build()
                )
        );

        DriverState driverState = driverService.createDriver(
                Optional.of("position-1"),
                Optional.of("firstName-1"),
                Optional.of("middleName-1"),
                Optional.of("lastName-1"),
                Optional.of(birthDate),
                Optional.of("ssn-1"),
                Optional.of(PaymentOptions.NONE),
                Optional.of(rate),
                Optional.of(DriverTypes.NONE),
                Optional.of(contactInfos),
                Optional.of(driverAddress),
                Optional.of(driverLicense)
        ).toCompletableFuture().get(5, SECONDS);

        assertNotNull(driverState.getId());
        assertEquals(Optional.of("position-1"), driverState.getPosition());
        assertEquals(Optional.of("firstName-1"), driverState.getFirstName());
        assertEquals(Optional.of("middleName-1"), driverState.getMiddleName());
        assertEquals(Optional.of("lastName-1"), driverState.getLastName());
        assertEquals(Optional.of(birthDate), driverState.getBirthDate());
        assertEquals(Optional.of("ssn-1"), driverState.getSsn());
        assertEquals(Optional.of(PaymentOptions.NONE), driverState.getPaymentOption());
        assertEquals(Optional.of(rate), driverState.getRate());
        assertEquals(Optional.of(DriverTypes.NONE), driverState.getDriverType());
        assertEquals(Optional.of(contactInfos), driverState.getContactInfo());
        assertEquals(Optional.of(driverAddress), driverState.getAddress());
        assertEquals(Optional.of(driverLicense), driverState.getLicense());


        Mockito.when(ref.ask(Mockito.any(UpdateDriver.class)))
                .thenReturn(CompletableFuture.completedFuture(
                        DriverState.builder().id("1")
                                .position("position-2")
                                .firstName("firstName-2")
                                .middleName("middleName-2")
                                .lastName("lastName-2")
                                .contactInfo(contactInfos)
                                .birthDate(birthDate)
                                .ssn("ssn-2")
                                .paymentOption(PaymentOptions.FLAT)
                                .rate(rate)
                                .address(driverAddress)
                                .license(driverLicense)
                                .driverType(DriverTypes.COMPANY_DRIVER)
                                .build()
                ));

        DriverState updatedDriverState = driverService
                .updateDriver(
                        driverState.getId(),
                        Optional.of("position-2"),
                        Optional.of("firstName-2"),
                        Optional.of("middleName-2"),
                        Optional.of("lastName-2"),
                        Optional.of(birthDate),
                        Optional.of("ssn-2"),
                        Optional.of(PaymentOptions.FLAT),
                        Optional.of(rate),
                        Optional.of(DriverTypes.COMPANY_DRIVER),
                        Optional.of(contactInfos),
                        Optional.of(driverAddress),
                        Optional.of(driverLicense)
                )
                .toCompletableFuture().get(5, SECONDS);

        assertEquals(Optional.of("position-2"), updatedDriverState.getPosition());
        assertEquals(Optional.of("firstName-2"), updatedDriverState.getFirstName());
        assertEquals(Optional.of("middleName-2"), updatedDriverState.getMiddleName());
        assertEquals(Optional.of("lastName-2"), updatedDriverState.getLastName());
        assertEquals(Optional.of(birthDate), updatedDriverState.getBirthDate());
        assertEquals(Optional.of("ssn-2"), updatedDriverState.getSsn());
        assertEquals(Optional.of(PaymentOptions.FLAT), updatedDriverState.getPaymentOption());
        assertEquals(Optional.of(rate), updatedDriverState.getRate());
        assertEquals(Optional.of(DriverTypes.COMPANY_DRIVER), updatedDriverState.getDriverType());
        assertEquals(Optional.of(contactInfos), updatedDriverState.getContactInfo());
        assertEquals(Optional.of(driverAddress), updatedDriverState.getAddress());
        assertEquals(Optional.of(driverLicense), updatedDriverState.getLicense());



        Mockito.when(ref.ask(Mockito.any(GetDriverInformation.class)))
                .thenReturn(CompletableFuture.completedFuture(
                        DriverState.builder().id("1")
                                .position("position-2")
                                .firstName("firstName-2")
                                .middleName("middleName-2")
                                .lastName("lastName-2")
                                .contactInfo(contactInfos)
                                .birthDate(birthDate)
                                .ssn("ssn-2")
                                .paymentOption(PaymentOptions.FLAT)
                                .rate(rate)
                                .address(driverAddress)
                                .license(driverLicense)
                                .driverType(DriverTypes.COMPANY_DRIVER)
                                .build()
                ));

        DriverState getDriverState = driverService.getDriverInformation(
                driverState.getId()
        ).toCompletableFuture().get(5, SECONDS);

        assertEquals(Optional.of("position-2"), getDriverState.getPosition());
        assertEquals(Optional.of("firstName-2"), getDriverState.getFirstName());
        assertEquals(Optional.of("middleName-2"), getDriverState.getMiddleName());
        assertEquals(Optional.of("lastName-2"), getDriverState.getLastName());
        assertEquals(Optional.of(birthDate), getDriverState.getBirthDate());
        assertEquals(Optional.of("ssn-2"), getDriverState.getSsn());
        assertEquals(Optional.of(PaymentOptions.FLAT), getDriverState.getPaymentOption());
        assertEquals(Optional.of(rate), getDriverState.getRate());
        assertEquals(Optional.of(DriverTypes.COMPANY_DRIVER), getDriverState.getDriverType());
        assertEquals(Optional.of(contactInfos), getDriverState.getContactInfo());
        assertEquals(Optional.of(driverAddress), getDriverState.getAddress());
        assertEquals(Optional.of(driverLicense), getDriverState.getLicense());
    }

    @Test
    public void testWithNull() throws InterruptedException, ExecutionException, TimeoutException {
        PersistentEntityRef ref = Mockito.mock(PersistentEntityRef.class);

        Mockito.when(persistentEntityRegistry.refFor(Mockito.any(), Mockito.any())).thenReturn(ref);

        Date birthDate = new Date();
        Date licenseExpiration = new Date();
        Date licenseDateIssued = new Date();
        Double rate = 0.324;
        Address driverAddress = Address.builder()
                .id(1)
                .name("addressName-1")
                .streetAddress1("streetAddress1-1")
                .streetAddress2("streetAddress2-1")
                .city("city-1")
                .phone("addressPhone-1")
                .state("state-1")
                .zip("zip-1")
                .fax("addressFax-1")
                .phoneExtension("addressPhoneExtension-1")
                .faxExtension("addressFaxExtension-1")
                .latitude(1)
                .longitude(1)
                .build();

        License driverLicense = License.builder()
                .licenseNumber("licenseNumber-1")
                .licenseExpiration(licenseExpiration)
                .licenseDateIssued(licenseDateIssued)
                .licenseStateIssued("licenseStateIssued-1")
                .licenseClass(LicenseClass.NONE)
                .licenseEndorsements("licenseEndorsements-1")
                .licenseRestrictions("licenseRestrictions-1")
                .build();

        PVector<ContactInfo> contactInfos = TreePVector.from(
                Arrays.asList(
                        ContactInfo.builder().label("label-1").value("value-1").type(ContactInfoType.NONE).build(),
                        ContactInfo.builder().label("label-2").value("value-2").type(ContactInfoType.NONE).build(),
                        ContactInfo.builder().label("label-3").value("value-3").type(ContactInfoType.NONE).build()
                )
        );
        Mockito.when(ref.ask(Mockito.any(UpdateDriver.class)))
                .thenReturn(CompletableFuture.completedFuture(
                        DriverState.builder()
                                .id("1")
                                .position(Optional.empty())
                                .firstName("firstName-2")
                                .middleName(Optional.empty())
                                .lastName("lastName-2")
                                .contactInfo(contactInfos)
                                .birthDate(Optional.empty())
                                .ssn(Optional.empty())
                                .paymentOption(PaymentOptions.FLAT)
                                .rate(Optional.empty())
                                .address(Optional.empty())
                                .license(driverLicense)
                                .driverType(DriverTypes.COMPANY_DRIVER)
                                .build()
                ));

        DriverState updatedDriverState = driverService
                .updateDriver(
                        "1",
                        Optional.empty(),
                        Optional.of("firstName-2"),
                        Optional.empty(),
                        Optional.of("lastName-2"),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(PaymentOptions.FLAT),
                        Optional.empty(),
                        Optional.of(DriverTypes.COMPANY_DRIVER),
                        Optional.of(contactInfos),
                        Optional.empty(),
                        Optional.of(driverLicense)
                )
                .toCompletableFuture().get(5, SECONDS);

        assertEquals(Optional.empty(), updatedDriverState.getPosition());
        assertEquals(Optional.of("firstName-2"), updatedDriverState.getFirstName());
        assertEquals(Optional.empty(), updatedDriverState.getMiddleName());
        assertEquals(Optional.of("lastName-2"), updatedDriverState.getLastName());
        assertEquals(Optional.empty(), updatedDriverState.getBirthDate());
        assertEquals(Optional.empty(), updatedDriverState.getSsn());
        assertEquals(Optional.of(PaymentOptions.FLAT), updatedDriverState.getPaymentOption());
        assertEquals(Optional.empty(), updatedDriverState.getRate());
        assertEquals(Optional.of(DriverTypes.COMPANY_DRIVER), updatedDriverState.getDriverType());
        assertEquals(Optional.of(contactInfos), updatedDriverState.getContactInfo());
        assertEquals(Optional.empty(), updatedDriverState.getAddress());
        assertEquals(Optional.of(driverLicense), updatedDriverState.getLicense());
    }
}
