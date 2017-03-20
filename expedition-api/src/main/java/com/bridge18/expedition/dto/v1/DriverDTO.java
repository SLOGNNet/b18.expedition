package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.driver.PaymentOptions;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.List;

@Immutable
public class DriverDTO {
    public String driverId;

    public String firstName;
    public String middleName;
    public String lastName;
    public List<ContactInfoDTO> contactInfo;
    public String position;
    public AddressDTO address;

    public Date birthDate;
    public String SSN;
    public PaymentOptions paymentOptions;
    public Double rate;
    public LicenseDTO license;

    public DriverDTO(String driverId, String firstName, String middleName, String lastName, List<ContactInfoDTO> contactInfo, String position, AddressDTO address, Date birthDate, String SSN, PaymentOptions paymentOptions, Double rate, LicenseDTO license) {
        this.driverId = driverId;
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
