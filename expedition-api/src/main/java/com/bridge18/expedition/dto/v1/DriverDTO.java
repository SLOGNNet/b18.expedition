package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.LicenseClass;
import com.bridge18.expedition.entities.PaymentOptions;

import javax.annotation.concurrent.Immutable;
import java.util.Date;

@Immutable
public class DriverDTO extends ContactDTO {
    public String driverId;

    public Date birthDate;
    public String SSN;
    public Integer paymentOptions;
    public Double rate;
    public LicenseDTO license;

    public DriverDTO(String driverId, Integer id, String firstName, String middleName, String lastName, String contactInfo, String position, AddressDTO address, Date birthDate, String SSN, Integer paymentOptions, Double rate, LicenseDTO license) {
        super(id, firstName, middleName, lastName, contactInfo, position, address);
        this.driverId = driverId;
        this.birthDate = birthDate;
        this.SSN = SSN;
        this.paymentOptions = paymentOptions;
        this.rate = rate;
        this.license = license;
    }
}
