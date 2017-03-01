package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.driver.*;

import javax.annotation.concurrent.Immutable;
import java.util.Date;

@Immutable
public class DriverDTO {
    public String id;

    public Boolean isActive;

    public String firstName;
    public String lastName;
    public Date birthDate;
    public String SSN;

    public DriverType driverType;
    public Date hireDate;
    public Date terminationDate;
    public EmploymentStatus status;

    public PaymentOptions paymentOptions;
    public Double rate;

    public String primaryPhone;
    public PhoneType primaryPhoneType;
    public String altPhone;
    public PhoneType altPhoneType;
    public String fax;
    public String email;

    public String streetAddress;
    public String secondStreetAddress;
    public String city;
    public String state;
    public String zip;

    public String licenseNumber;
    public Date licenseExpiration;
    public Date licenseDateIssued;
    public LicenseStateIssue licenseStateIssue;
    public LicenseClass licenseClass;
    public LicenseEndorsements licenseEndorsements;

    public DriverDTO(String id) {
        this.id = id;
    }

    public DriverDTO(String id, Boolean isActive, String firstName, String lastName, Date birthDate, String SSN, DriverType driverType, Date hireDate, Date terminationDate, EmploymentStatus status, PaymentOptions paymentOptions, Double rate, String primaryPhone, PhoneType primaryPhoneType, String altPhone, PhoneType altPhoneType, String fax, String email, String streetAddress, String secondStreetAddress, String city, String state, String zip, String licenseNumber, Date licenseExpiration, Date licenseDateIssued, LicenseStateIssue licenseStateIssue, LicenseClass licenseClass, LicenseEndorsements licenseEndorsements) {
        this.id = id;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.SSN = SSN;
        this.driverType = driverType;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.status = status;
        this.paymentOptions = paymentOptions;
        this.rate = rate;
        this.primaryPhone = primaryPhone;
        this.primaryPhoneType = primaryPhoneType;
        this.altPhone = altPhone;
        this.altPhoneType = altPhoneType;
        this.fax = fax;
        this.email = email;
        this.streetAddress = streetAddress;
        this.secondStreetAddress = secondStreetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.licenseNumber = licenseNumber;
        this.licenseExpiration = licenseExpiration;
        this.licenseDateIssued = licenseDateIssued;
        this.licenseStateIssue = licenseStateIssue;
        this.licenseClass = licenseClass;
        this.licenseEndorsements = licenseEndorsements;
    }

    public DriverDTO(String id, Boolean isActive, String firstName, String lastName) {
        this.id = id;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
