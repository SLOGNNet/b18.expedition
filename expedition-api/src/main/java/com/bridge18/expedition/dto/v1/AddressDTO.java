package com.bridge18.expedition.dto.v1;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AddressDTO {
    public Integer id;
    public String name;
    public String streetAddress1;
    public String streetAddress2;
    public String city;
    public String phone;
    public String state;
    public String zip;
    public String fax;
    public String phoneExtension;
    public String faxExtension;
    public Double latitude;
    public Double longitude;

    public AddressDTO() {
    }

    public AddressDTO(Integer id, String name, String streetAddress1, String streetAddress2, String city, String phone, String state, String zip, String fax, String phoneExtension, String faxExtension, Double latitude, Double longitude) {
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
