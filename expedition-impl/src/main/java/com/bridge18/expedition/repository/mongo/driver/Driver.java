package com.bridge18.expedition.repository.mongo.driver;

import com.bridge18.expedition.entities.driver.DriverTypes;
import com.bridge18.expedition.entities.driver.PaymentOptions;
import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;
import java.util.List;

//@Entity("drivers")
@Data
public class Driver {
    @Id
    private String id;

    private String driverId;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<MongoContactInfo> contactInfo;
    private String position;
    private Date birthDate;
    private String ssn;
    private PaymentOptions paymentOption;
    private Double rate;
    private DriverTypes type;

    @Embedded
    private MongoAddress address;
    @Embedded
    private MongoLicense license;

    public Driver(String driverId, String firstName, String middleName, String lastName, List<MongoContactInfo> mongoContactInfo, String position, Date birthDate, String ssn, PaymentOptions paymentOption, Double rate, DriverTypes type, MongoAddress mongoAddress, MongoLicense mongoLicense) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.contactInfo = mongoContactInfo;
        this.position = position;
        this.birthDate = birthDate;
        this.ssn = ssn;
        this.paymentOption = paymentOption;
        this.rate = rate;
        this.type = type;
        this.address = mongoAddress;
        this.license = mongoLicense;
    }

    public Driver() {
    }
}
