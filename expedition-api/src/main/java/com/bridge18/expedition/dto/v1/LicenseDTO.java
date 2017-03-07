package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.LicenseClass;

import java.util.Date;

public class LicenseDTO {
    public Integer number;
    public Date expiration;
    public Date dateIssues;
    public String stateIssued;
    public Integer licenseClass;
    public String endorsements;
    public String restrictions;

    public LicenseDTO(Integer number, Date expiration, Date dateIssues, String stateIssued, Integer licenseClass, String endorsements, String restrictions) {
        this.number = number;
        this.expiration = expiration;
        this.dateIssues = dateIssues;
        this.stateIssued = stateIssued;
        this.licenseClass = licenseClass;
        this.endorsements = endorsements;
        this.restrictions = restrictions;
    }
}
