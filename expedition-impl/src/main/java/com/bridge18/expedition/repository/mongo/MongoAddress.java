package com.bridge18.expedition.repository.mongo;

import lombok.Data;

@Data
public class MongoAddress {
    private Integer id;
    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String phone;
    private String state;
    private String zip;
    private String fax;
    private String phoneExtension;
    private String faxExtension;
    private Double latitude;
    private Double longitude;

    public MongoAddress() {
    }

    public MongoAddress(Integer id, String name, String streetAddress1, String streetAddress2, String city, String phone, String state, String zip, String fax, String phoneExtension, String faxExtension, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.phone = phone;
        this.state = state;
        this.zip = zip;
        this.fax = fax;
        this.phoneExtension = phoneExtension;
        this.faxExtension = faxExtension;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
