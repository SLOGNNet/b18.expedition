package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.driver.LicenseClass;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LicenseDTO {
    public String number;
    public Date expiration;
    public Date dateIssued;
    public String stateIssued;
    public LicenseClass licenseClass;
    public String endorsements;
    public String restrictions;
}
