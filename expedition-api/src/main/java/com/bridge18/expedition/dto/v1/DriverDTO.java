package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.driver.DriverTypes;
import com.bridge18.expedition.entities.driver.PaymentOptions;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.Pattern;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Immutable
@EqualsAndHashCode
public class DriverDTO {
    public String id;

    @Required(message = "Firstname can't be empty")
    public String firstName;

    public String middleName;

    @Required(message = "Middlename can't be empty")
    public String lastName;

    public List<ContactInfoDTO> contactInfo;
    public String position;
    public AddressDTO address;
    public Date birthDate;

    @Pattern(value = "^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$", message = "SSN isn't correct")
    public String ssn;

    public PaymentOptions paymentOption;
    public Double rate;
    public DriverTypes type;

    @Required(message = "License can't be empty")
    public LicenseDTO license;
}
