package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.driver.DriverTypes;
import com.bridge18.expedition.entities.driver.PaymentOptions;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Immutable
@EqualsAndHashCode
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
    public DriverTypes type;
    public LicenseDTO license;
}
