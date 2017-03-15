package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.LicenseClass;
import com.bridge18.expedition.entities.PaymentOptions;

import javax.annotation.concurrent.Immutable;
import java.util.Date;

@Immutable
public class DriverDTO {
    public String driverId;

    public Integer id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String contactInfo;
    public String position;
    public AddressDTO address;

    public Date birthDate;
    public String SSN;
    public Integer paymentOptions;
    public Double rate;
    public LicenseDTO license;

    public DriverDTO(String driverId, Integer id, String firstName, String middleName, String lastName, String contactInfo, String position, AddressDTO address, Date birthDate, String SSN, Integer paymentOptions, Double rate, LicenseDTO license) {
        this.driverId = driverId;
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.contactInfo = contactInfo;
        this.position = position;
        this.address = address;
        this.birthDate = birthDate;
        this.SSN = SSN;
        this.paymentOptions = paymentOptions;
        this.rate = rate;
        this.license = license;
    }
}
