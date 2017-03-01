package com.bridge18.expedition.driver;


import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import com.bridge18.expedition.entities.driver.*;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver.Outcome;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

public class DriverEntityTest {
    static ActorSystem system;

    @BeforeClass
    public static void setup(){
        system = ActorSystem.create("DriverEntityTest");
    }

    @AfterClass
    public static void teardown(){
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void test() {
        PersistentEntityTestDriver<DriverCommand, DriverEvent, DriverState> persistentEntityTestDriver =
                new PersistentEntityTestDriver(system, new DriverEntity(), "driver-1");

        Date birthDate = new Date();
        Date hireDate = new Date();
        Date terminationDate = new Date();
        Date licenseExpiration = new Date();
        Date licenseDateIssued = new Date();
        Double rate = 0.324;
        DriverCommand.CreateDriver cmd = new DriverCommand.CreateDriver(Optional.of("firstName-1"), Optional.of("lastName-1"),
                Optional.of(birthDate), Optional.of("SSN-1"), Optional.of(DriverType.COMPANY),
                Optional.of(hireDate), Optional.of(terminationDate), Optional.of(EmploymentStatus.ACTIVE),
                Optional.of(PaymentOptions.PER_MILE), Optional.of(rate), Optional.of("primaryPhone-1"),
                Optional.of(PhoneType.CELL), Optional.of("altPhone-1"), Optional.of(PhoneType.CELL),
                Optional.of("fax-1"), Optional.of("email-1"), Optional.of("streetAddress-1"),
                Optional.of("secondStreetAddress-1"), Optional.of("city-1"), Optional.of("state-1"),
                Optional.of("zip-1"), Optional.of("licenseNumber-1"), Optional.of(licenseExpiration),
                Optional.of(licenseDateIssued), Optional.of(LicenseStateIssue.AL),
                Optional.of(LicenseClass.CLASS_A), Optional.of(LicenseEndorsements.H));
        Outcome<DriverEvent, DriverState> outcome1 = persistentEntityTestDriver.run(cmd);

        assertEquals(1, outcome1.events().size());

        DriverState driverState = (DriverState) outcome1.getReplies().get(0);
        assertEquals("driver-1", ((DriverState) outcome1.getReplies().get(0)).getId());
        assertEquals(Optional.of(true), driverState.getIsActive());
        assertEquals(Optional.of("firstName-1"), driverState.getFirstName());
        assertEquals(Optional.of("lastName-1"), driverState.getLastName());
        assertEquals(Optional.of(birthDate), driverState.getBirthDate());
        assertEquals(Optional.of("SSN-1"), driverState.getSSN());
        assertEquals(Optional.of(DriverType.COMPANY), driverState.getDriverType());
        assertEquals(Optional.of(hireDate), driverState.getHireDate());
        assertEquals(Optional.of(terminationDate), driverState.getTerminationDate());
        assertEquals(Optional.of(EmploymentStatus.ACTIVE), driverState.getStatus());
        assertEquals(Optional.of(PaymentOptions.PER_MILE), driverState.getPaymentOptions());
        assertEquals(Optional.of(rate), driverState.getRate());
        assertEquals(Optional.of("primaryPhone-1"), driverState.getPrimaryPhone());
        assertEquals(Optional.of(PhoneType.CELL), driverState.getPrimaryPhoneType());
        assertEquals(Optional.of("fax-1"), driverState.getFax());
        assertEquals(Optional.of("email-1"), driverState.getEmail());
        assertEquals(Optional.of("streetAddress-1"), driverState.getStreetAddress());
        assertEquals(Optional.of("secondStreetAddress-1"), driverState.getSecondStreetAddress());
        assertEquals(Optional.of("city-1"), driverState.getCity());
        assertEquals(Optional.of("state-1"), driverState.getState());
        assertEquals(Optional.of("zip-1"), driverState.getZip());
        assertEquals(Optional.of("licenseNumber-1"), driverState.getLicenseNumber());
        assertEquals(Optional.of(licenseExpiration), driverState.getLicenseExpiration());
        assertEquals(Optional.of(licenseDateIssued), driverState.getLicenseDateIssued());
        assertEquals(Optional.of(LicenseStateIssue.AL), driverState.getLicenseStateIssue());
        assertEquals(Optional.of(LicenseClass.CLASS_A), driverState.getLicenseClass());
        assertEquals(Optional.of(LicenseEndorsements.H), driverState.getLicenseEndorsements());

        assertEquals(Collections.emptyList(), outcome1.issues());


        Date birthDate2 = new Date();
        Date hireDate2 = new Date();
        Date terminationDate2 = new Date();
        Date licenseExpiration2 = new Date();
        Date licenseDateIssued2 = new Date();
        Double rate2 = 0.324;
        DriverCommand.ChangeDriverInformation changeDriverInformationCmd = new DriverCommand.ChangeDriverInformation(Optional.of("firstName-2"), Optional.of("lastName-2"),
                Optional.of(birthDate2), Optional.of("SSN-2"), Optional.of(DriverType.COMPANY),
                Optional.of(hireDate2), Optional.of(terminationDate2), Optional.of(EmploymentStatus.ACTIVE),
                Optional.of(PaymentOptions.PER_MILE), Optional.of(rate2), Optional.of("primaryPhone-2"),
                Optional.of(PhoneType.CELL), Optional.of("altPhone-2"), Optional.of(PhoneType.CELL),
                Optional.of("fax-2"), Optional.of("email-2"), Optional.of("streetAddress-2"),
                Optional.of("secondStreetAddress-2"), Optional.of("city-2"), Optional.of("state-2"),
                Optional.of("zip-2"), Optional.of("licenseNumber-2"), Optional.of(licenseExpiration2),
                Optional.of(licenseDateIssued2), Optional.of(LicenseStateIssue.AL),
                Optional.of(LicenseClass.CLASS_A), Optional.of(LicenseEndorsements.H));
        Outcome<DriverEvent, DriverState> outcome2 = persistentEntityTestDriver.run(changeDriverInformationCmd);

        assertEquals(1, outcome2.events().size());

        driverState = (DriverState) outcome2.getReplies().get(0);

        assertEquals("driver-1", ((DriverState) outcome2.getReplies().get(0)).getId());
        assertEquals(Optional.of(true), driverState.getIsActive());
        assertEquals(Optional.of("firstName-2"), driverState.getFirstName());
        assertEquals(Optional.of("lastName-2"), driverState.getLastName());
        assertEquals(Optional.of(birthDate2), driverState.getBirthDate());
        assertEquals(Optional.of("SSN-2"), driverState.getSSN());
        assertEquals(Optional.of(DriverType.COMPANY), driverState.getDriverType());
        assertEquals(Optional.of(hireDate2), driverState.getHireDate());
        assertEquals(Optional.of(terminationDate2), driverState.getTerminationDate());
        assertEquals(Optional.of(EmploymentStatus.ACTIVE), driverState.getStatus());
        assertEquals(Optional.of(PaymentOptions.PER_MILE), driverState.getPaymentOptions());
        assertEquals(Optional.of(rate2), driverState.getRate());
        assertEquals(Optional.of("primaryPhone-2"), driverState.getPrimaryPhone());
        assertEquals(Optional.of(PhoneType.CELL), driverState.getPrimaryPhoneType());
        assertEquals(Optional.of("fax-2"), driverState.getFax());
        assertEquals(Optional.of("email-2"), driverState.getEmail());
        assertEquals(Optional.of("streetAddress-2"), driverState.getStreetAddress());
        assertEquals(Optional.of("secondStreetAddress-2"), driverState.getSecondStreetAddress());
        assertEquals(Optional.of("city-2"), driverState.getCity());
        assertEquals(Optional.of("state-2"), driverState.getState());
        assertEquals(Optional.of("zip-2"), driverState.getZip());
        assertEquals(Optional.of("licenseNumber-2"), driverState.getLicenseNumber());
        assertEquals(Optional.of(licenseExpiration2), driverState.getLicenseExpiration());
        assertEquals(Optional.of(licenseDateIssued2), driverState.getLicenseDateIssued());
        assertEquals(Optional.of(LicenseStateIssue.AL), driverState.getLicenseStateIssue());
        assertEquals(Optional.of(LicenseClass.CLASS_A), driverState.getLicenseClass());
        assertEquals(Optional.of(LicenseEndorsements.H), driverState.getLicenseEndorsements());

        assertEquals(1, outcome2.getReplies().size());
        assertEquals(Collections.emptyList(), outcome2.issues());


        DriverCommand.GetDriverInformation getDriverCmd = new DriverCommand.GetDriverInformation();
        Outcome<DriverEvent, DriverState> outcome3 = persistentEntityTestDriver.run(getDriverCmd);

        assertEquals(0, outcome3.events().size());

        driverState = (DriverState) outcome3.getReplies().get(0);

        assertEquals("driver-1", ((DriverState) outcome3.getReplies().get(0)).getId());
        assertEquals(Optional.of(true), driverState.getIsActive());
        assertEquals(Optional.of("firstName-2"), driverState.getFirstName());
        assertEquals(Optional.of("lastName-2"), driverState.getLastName());
        assertEquals(Optional.of(birthDate2), driverState.getBirthDate());
        assertEquals(Optional.of("SSN-2"), driverState.getSSN());
        assertEquals(Optional.of(DriverType.COMPANY), driverState.getDriverType());
        assertEquals(Optional.of(hireDate2), driverState.getHireDate());
        assertEquals(Optional.of(terminationDate2), driverState.getTerminationDate());
        assertEquals(Optional.of(EmploymentStatus.ACTIVE), driverState.getStatus());
        assertEquals(Optional.of(PaymentOptions.PER_MILE), driverState.getPaymentOptions());
        assertEquals(Optional.of(rate2), driverState.getRate());
        assertEquals(Optional.of("primaryPhone-2"), driverState.getPrimaryPhone());
        assertEquals(Optional.of(PhoneType.CELL), driverState.getPrimaryPhoneType());
        assertEquals(Optional.of("fax-2"), driverState.getFax());
        assertEquals(Optional.of("email-2"), driverState.getEmail());
        assertEquals(Optional.of("streetAddress-2"), driverState.getStreetAddress());
        assertEquals(Optional.of("secondStreetAddress-2"), driverState.getSecondStreetAddress());
        assertEquals(Optional.of("city-2"), driverState.getCity());
        assertEquals(Optional.of("state-2"), driverState.getState());
        assertEquals(Optional.of("zip-2"), driverState.getZip());
        assertEquals(Optional.of("licenseNumber-2"), driverState.getLicenseNumber());
        assertEquals(Optional.of(licenseExpiration2), driverState.getLicenseExpiration());
        assertEquals(Optional.of(licenseDateIssued2), driverState.getLicenseDateIssued());
        assertEquals(Optional.of(LicenseStateIssue.AL), driverState.getLicenseStateIssue());
        assertEquals(Optional.of(LicenseClass.CLASS_A), driverState.getLicenseClass());
        assertEquals(Optional.of(LicenseEndorsements.H), driverState.getLicenseEndorsements());

        assertEquals(1, outcome2.getReplies().size());
        assertEquals(Collections.emptyList(), outcome2.issues());



        DriverCommand.DisableDriver disableDriverCmd = new DriverCommand.DisableDriver(Optional.of("No use"));
        Outcome<DriverEvent, DriverState> outcome4 = persistentEntityTestDriver.run(disableDriverCmd);
        assertEquals(1, outcome4.events().size());
        assertEquals(new DriverEvent.DriverDisabled(Optional.of("No use")),
                outcome4.events().get(0));

        Outcome<DriverEvent, DriverState> outcome5 = persistentEntityTestDriver.run(getDriverCmd);
        assertEquals(Optional.of(false), outcome5.state().getIsActive());
    }
}
