package com.bridge18.expedition.driver;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import com.bridge18.expedition.entities.driver.*;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DriverEntityTest {
    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("DriverEntityTest");
    }

    @AfterClass
    public static void teardown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testBlockingOfCommandsBeforeCreation(){
        PersistentEntityTestDriver<DriverCommand, DriverEvent, DriverState> persistentEntityTestDriver =
                new PersistentEntityTestDriver(system, new DriverEntity(), "test-driver-2");

        GetDriverInformation getDriverCmd = GetDriverInformation.builder().build();
        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> getOutcome = persistentEntityTestDriver.run(getDriverCmd);
        assertTrue(getOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);

        UpdateDriver updateDriverCmd = UpdateDriver.builder().build();
        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> updateOutcome = persistentEntityTestDriver.run(updateDriverCmd);
        assertTrue(updateOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);

        DeleteDriver deleteDriverCmd = DeleteDriver.builder().build();
        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> deleteOutcome = persistentEntityTestDriver.run(deleteDriverCmd);
        assertTrue(deleteOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);
    }

    @Test
    public void testBlockingOfCommandsAfterDeletion(){
        PersistentEntityTestDriver<DriverCommand, DriverEvent, DriverState> persistentEntityTestDriver =
                new PersistentEntityTestDriver(system, new DriverEntity(), "test-driver-3");

        CreateDriver createDriverCmd = CreateDriver.builder().build();
        persistentEntityTestDriver.run(createDriverCmd);

        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> createOutcome = persistentEntityTestDriver.run(createDriverCmd);
        assertTrue(createOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);


        DeleteDriver deleteDriverCmd = DeleteDriver.builder().build();
        persistentEntityTestDriver.run(deleteDriverCmd);


        GetDriverInformation getDriverCmd = GetDriverInformation.builder().build();
        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> getOutcome = persistentEntityTestDriver.run(getDriverCmd);
        assertTrue(getOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);

        UpdateDriver updateDriverCmd = UpdateDriver.builder().build();
        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> updateOutcome = persistentEntityTestDriver.run(updateDriverCmd);
        assertTrue(updateOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);

        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> createOutcome_2 = persistentEntityTestDriver.run(createDriverCmd);
        assertTrue(createOutcome_2.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);

    }


    @Test
    public void test() {
        PersistentEntityTestDriver<DriverCommand, DriverEvent, DriverState> persistentEntityTestDriver =
                new PersistentEntityTestDriver(system, new DriverEntity(), "test-driver-1");

        Date birthDate = new Date();
        Date licenseExpiration = new Date();
        Date licenseDateIssued = new Date();
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

        Double rate = 0.324;
        CreateDriver createDriverCmd = CreateDriver.builder()
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
                .build();

        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> outcome1 = persistentEntityTestDriver.run(createDriverCmd);

        assertEquals(2, outcome1.events().size());

        DriverState driverState = (DriverState) outcome1.getReplies().get(0);
        assertEquals("test-driver-1", ((DriverState) outcome1.getReplies().get(0)).getId());
        assertEquals(Optional.of("firstName-1"), driverState.getFirstName());
        assertEquals(Optional.of("middleName-1"), driverState.getMiddleName());
        assertEquals(Optional.of("lastName-1"), driverState.getLastName());
        assertEquals(Optional.of(birthDate), driverState.getBirthDate());
        assertEquals(Optional.of("ssn-1"), driverState.getSsn());
        assertEquals(Optional.of(DriverTypes.NONE), driverState.getDriverType());
        assertEquals(Optional.of(PaymentOptions.NONE), driverState.getPaymentOption());
        assertEquals(Optional.of(rate), driverState.getRate());
        assertEquals(Optional.of(contactInfos), driverState.getContactInfo());
        assertEquals(Optional.of(driverAddress), driverState.getAddress());
        assertEquals(Optional.of(driverLicense), driverState.getLicense());

        assertEquals(Collections.emptyList(), outcome1.issues());


        Date birthDate2 = new Date();
        Date licenseExpiration2 = new Date();
        Date licenseDateIssued2 = new Date();
        Double rate2 = 0.324;
        Address driverAddress2 = Address.builder()
                .id(2)
                .name("addressName-2")
                .streetAddress1("streetAddress1-2")
                .streetAddress2("streetAddress2-2")
                .city("city-2")
                .phone("addressPhone-2")
                .state("state-2")
                .zip("zip-2")
                .fax("addressFax-2")
                .phoneExtension("addressPhoneExtension-2")
                .faxExtension("addressFaxExtension-2")
                .latitude(2)
                .longitude(2)
                .build();

        License driverLicense2 = License.builder()
                .licenseNumber("licenseNumber-2")
                .licenseExpiration(licenseExpiration2)
                .licenseDateIssued(licenseDateIssued2)
                .licenseStateIssued("licenseStateIssued-2")
                .licenseClass(LicenseClass.CLASS_A)
                .licenseEndorsements("licenseEndorsements-2")
                .licenseRestrictions("licenseRestrictions-2")
                .build();

        PVector<ContactInfo> contactInfos2 = TreePVector.from(
                Arrays.asList(
                        ContactInfo.builder().label("label-4").value("value-4").type(ContactInfoType.EMAIL).build(),
                        ContactInfo.builder().label("label-5").value("value-5").type(ContactInfoType.EMAIL).build(),
                        ContactInfo.builder().label("label-6").value("value-6").type(ContactInfoType.EMAIL).build()
                )
        );

        UpdateDriver updateDriverCmd = UpdateDriver.builder()
                .position("position-2")
                .firstName("firstName-2")
                .middleName("middleName-2")
                .lastName("lastName-2")
                .contactInfo(contactInfos2)
                .birthDate(birthDate2)
                .ssn("ssn-2")
                .paymentOption(PaymentOptions.FLAT)
                .rate(rate2)
                .address(driverAddress2)
                .license(driverLicense2)
                .driverType(DriverTypes.COMPANY_DRIVER)
                .build();
        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> outcome2 = persistentEntityTestDriver.run(updateDriverCmd);

        assertEquals(1, outcome2.events().size());

        driverState = (DriverState) outcome2.getReplies().get(0);

        assertEquals("test-driver-1", ((DriverState) outcome2.getReplies().get(0)).getId());
        assertEquals(Optional.of("firstName-2"), driverState.getFirstName());
        assertEquals(Optional.of("middleName-2"), driverState.getMiddleName());
        assertEquals(Optional.of("lastName-2"), driverState.getLastName());
        assertEquals(Optional.of(birthDate2), driverState.getBirthDate());
        assertEquals(Optional.of("ssn-2"), driverState.getSsn());
        assertEquals(Optional.of(DriverTypes.COMPANY_DRIVER), driverState.getDriverType());
        assertEquals(Optional.of(PaymentOptions.FLAT), driverState.getPaymentOption());
        assertEquals(Optional.of(rate2), driverState.getRate());
        assertEquals(Optional.of(contactInfos2), driverState.getContactInfo());
        assertEquals(Optional.of(driverAddress2), driverState.getAddress());
        assertEquals(Optional.of(driverLicense2), driverState.getLicense());

        assertEquals(1, outcome2.getReplies().size());
        assertEquals(Collections.emptyList(), outcome2.issues());


        GetDriverInformation getDriverCmd = GetDriverInformation.builder().build();
        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> outcome3 = persistentEntityTestDriver.run(getDriverCmd);

        assertEquals(0, outcome3.events().size());

        driverState = (DriverState) outcome3.getReplies().get(0);

        assertEquals("test-driver-1", ((DriverState) outcome3.getReplies().get(0)).getId());
        assertEquals(Optional.of("firstName-2"), driverState.getFirstName());
        assertEquals(Optional.of("middleName-2"), driverState.getMiddleName());
        assertEquals(Optional.of("lastName-2"), driverState.getLastName());
        assertEquals(Optional.of(birthDate2), driverState.getBirthDate());
        assertEquals(Optional.of("ssn-2"), driverState.getSsn());
        assertEquals(Optional.of(DriverTypes.COMPANY_DRIVER), driverState.getDriverType());
        assertEquals(Optional.of(PaymentOptions.FLAT), driverState.getPaymentOption());
        assertEquals(Optional.of(rate2), driverState.getRate());
        assertEquals(Optional.of(contactInfos2), driverState.getContactInfo());
        assertEquals(Optional.of(driverAddress2), driverState.getAddress());
        assertEquals(Optional.of(driverLicense2), driverState.getLicense());

        assertEquals(1, outcome2.getReplies().size());
        assertEquals(Collections.emptyList(), outcome2.issues());


        DeleteDriver deleteDriverCmd = DeleteDriver.builder().build();
        PersistentEntityTestDriver.Outcome<DriverEvent, DriverState> outcome4 = persistentEntityTestDriver.run(deleteDriverCmd);
        assertEquals(1, outcome4.events().size());
        assertEquals(DriverDeleted.builder().id("test-driver-1").build(),
                outcome4.events().get(0));
    }
}
