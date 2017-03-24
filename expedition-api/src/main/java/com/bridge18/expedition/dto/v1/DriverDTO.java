package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.driver.PaymentOptions;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.List;

@Immutable
public class DriverDTO {
    public String id;

    public String firstName;
    public String middleName;
    public String lastName;
    public List<ContactInfoDTO> contactInfo;
    public String position;
    public AddressDTO address;

    public Date birthDate;
    public String ssn;
    public PaymentOptions paymentOption;
    public Double rate;
    public LicenseDTO license;

    public DriverDTO(String id, String firstName, String middleName, String lastName, List<ContactInfoDTO> contactInfo, String position, AddressDTO address, Date birthDate, String ssn, PaymentOptions paymentOption, Double rate, LicenseDTO license) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.contactInfo = contactInfo;
        this.position = position;
        this.address = address;
        this.birthDate = birthDate;
        this.ssn = ssn;
        this.paymentOption = paymentOption;
        this.rate = rate;
        this.license = license;
    }
}
