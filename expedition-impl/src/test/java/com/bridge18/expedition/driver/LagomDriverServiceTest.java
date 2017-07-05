package com.bridge18.expedition.driver;

import akka.actor.ActorSystem;
import com.bridge18.expedition.api.LagomDriverService;
import com.bridge18.expedition.dto.v1.*;
import com.bridge18.expedition.entities.driver.ContactInfoType;
import com.bridge18.expedition.entities.driver.DriverTypes;
import com.bridge18.expedition.entities.driver.LicenseClass;
import com.bridge18.expedition.entities.driver.PaymentOptions;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.inject.Bindings.bind;

public class LagomDriverServiceTest {
    static ActorSystem system;

    private final static ServiceTest.Setup setup = defaultSetup().withCassandra(true)
            .configureBuilder(b ->
                    b.configure("cassandra-query-journal.eventual-consistency-delay", "0")
                            .overrides(bind(ReadSide.class).to(Mockito.mock(ReadSide.class).getClass()))
            );

    private static ServiceTest.TestServer testServer;
    private static LagomDriverService testService;


    @BeforeClass
    public static void beforeAll() {
        system = ActorSystem.create("LagomDriverServiceTest");

        testServer = ServiceTest.startServer(setup);
        testService = testServer.client(LagomDriverService.class);
    }

    @Test
    public void test() throws InterruptedException, ExecutionException, TimeoutException {
        Date birthDate = new Date();
        Date licenseExpiration = new Date();
        Date licenseDateIssued = new Date();
        Double rate = 0.324;
        AddressDTO driverAddressDTO = new AddressDTO(
                1, "addressName-1", "streetAddress1-1", "streetAddress2-1", "city-1",
                "addressPhone-1", "state-1", "zip-1", "addressFax-1", "addressPhoneExtension-1",
                "addressFaxExtension-1", 1.0, 1.0
        );

        LicenseDTO driverLicenseDTO = new LicenseDTO(
                "licenseNumber-1", licenseExpiration, licenseDateIssued, "licenseStateIssued-1",
                LicenseClass.NONE, "licenseEndorsements-1", "licenseEndorsements-1"
        );

        List<ContactInfoDTO> contactInfos = Arrays.asList(
                new ContactInfoDTO("label-1", "value-1", ContactInfoType.NONE),
                new ContactInfoDTO("label-2", "value-2", ContactInfoType.NONE)
        );
        DriverDTO inputDriverDTO = new DriverDTO(null, "firstName-1", "middleName-1",
                "lastName-1", contactInfos, "position-1", driverAddressDTO,
                birthDate, "ssn-1", PaymentOptions.NONE, rate, DriverTypes.NONE, driverLicenseDTO);
        DriverDTO createdDriverDTO = testService.createNewDriver().invoke(inputDriverDTO)
                .toCompletableFuture().get(10, SECONDS);

        assertNotNull(createdDriverDTO.id);
        assertEquals("position-1", createdDriverDTO.position);
        assertEquals("firstName-1", createdDriverDTO.firstName);
        assertEquals("middleName-1", createdDriverDTO.middleName);
        assertEquals("lastName-1", createdDriverDTO.lastName);
        assertEquals(birthDate, createdDriverDTO.birthDate);
        assertEquals("ssn-1", createdDriverDTO.ssn);
        assertEquals(PaymentOptions.NONE, createdDriverDTO.paymentOption);
        assertEquals(rate, createdDriverDTO.rate);
        assertEquals(DriverTypes.NONE, createdDriverDTO.type);
        assertEquals(contactInfos, createdDriverDTO.contactInfo);
        assertEquals(driverAddressDTO, createdDriverDTO.address);
        assertEquals(driverLicenseDTO, createdDriverDTO.license);

        inputDriverDTO = new DriverDTO(null, "firstName-2", "middleName-2",
                "lastName-2", contactInfos, "position-2", driverAddressDTO,
                birthDate, "ssn-2", PaymentOptions.NONE, rate, DriverTypes.NONE, driverLicenseDTO);
        DriverDTO updatedDriverDTO = testService.updateDriverInformation(createdDriverDTO.id)
                .invoke(inputDriverDTO).toCompletableFuture().get(10, SECONDS);

        assertNotNull(updatedDriverDTO.id);
        assertEquals("position-2", updatedDriverDTO.position);
        assertEquals("firstName-2", updatedDriverDTO.firstName);
        assertEquals("middleName-2", updatedDriverDTO.middleName);
        assertEquals("lastName-2", updatedDriverDTO.lastName);
        assertEquals(birthDate, updatedDriverDTO.birthDate);
        assertEquals("ssn-2", updatedDriverDTO.ssn);
        assertEquals(PaymentOptions.NONE, updatedDriverDTO.paymentOption);
        assertEquals(rate, updatedDriverDTO.rate);
        assertEquals(DriverTypes.NONE, updatedDriverDTO.type);
        assertEquals(contactInfos, updatedDriverDTO.contactInfo);
        assertEquals(driverAddressDTO, updatedDriverDTO.address);
        assertEquals(driverLicenseDTO, updatedDriverDTO.license);

        DriverDTO getDriverDTO = testService.getDriverInformation(createdDriverDTO.id)
                .invoke().toCompletableFuture().get(10, SECONDS);
        assertNotNull(updatedDriverDTO.id);
        assertEquals("position-2", getDriverDTO.position);
        assertEquals("firstName-2", getDriverDTO.firstName);
        assertEquals("middleName-2", getDriverDTO.middleName);
        assertEquals("lastName-2", getDriverDTO.lastName);
        assertEquals(birthDate, getDriverDTO.birthDate);
        assertEquals("ssn-2", getDriverDTO.ssn);
        assertEquals(PaymentOptions.NONE, getDriverDTO.paymentOption);
        assertEquals(rate, getDriverDTO.rate);
        assertEquals(DriverTypes.NONE, getDriverDTO.type);
        assertEquals(contactInfos, getDriverDTO.contactInfo);
        assertEquals(driverAddressDTO, getDriverDTO.address);
        assertEquals(driverLicenseDTO, getDriverDTO.license);
    }
}
