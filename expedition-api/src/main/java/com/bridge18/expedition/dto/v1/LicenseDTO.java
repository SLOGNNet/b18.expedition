package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.driver.LicenseClass;

import java.util.Date;

public class LicenseDTO {
    public String number;
    public Date expiration;
    public Date dateIssued;
    public String stateIssued;
    public LicenseClass licenseClass;
    public String endorsements;
    public String restrictions;

    public LicenseDTO() {
    }

    public LicenseDTO(String number, Date expiration, Date dateIssued, String stateIssued, LicenseClass licenseClass, String endorsements, String restrictions) {
        this.number = number;
        this.expiration = expiration;
        this.dateIssued = dateIssued;
        this.stateIssued = stateIssued;
        this.licenseClass = licenseClass;
        this.endorsements = endorsements;
        this.restrictions = restrictions;
    }
}
