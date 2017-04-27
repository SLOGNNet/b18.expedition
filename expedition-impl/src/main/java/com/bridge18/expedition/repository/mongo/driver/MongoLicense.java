package com.bridge18.expedition.repository.mongo.driver;

import com.bridge18.expedition.entities.driver.LicenseClass;
import lombok.Data;

import java.util.Date;

@Data
public class MongoLicense {
    private String number;
    private Date expiration;
    private Date dateIssued;
    private String stateIssued;
    private LicenseClass licenseClass;
    private String endorsements;
    private String restrictions;

    public MongoLicense() {
    }

    public MongoLicense(String number, Date expiration, Date dateIssued, String stateIssued, LicenseClass licenseClass, String endorsements, String restrictions) {
        this.number = number;
        this.expiration = expiration;
        this.dateIssued = dateIssued;
        this.stateIssued = stateIssued;
        this.licenseClass = licenseClass;
        this.endorsements = endorsements;
        this.restrictions = restrictions;
    }
}
