package com.bridge18.expedition.dto.v1;

import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public final class DriverSummaryDTO {
    String id;
    String firstName;
    String lastName;
    AddressDTO addressDTO;

    public DriverSummaryDTO() {
    }

    public DriverSummaryDTO(String id, String firstName, String lastName, AddressDTO addressDTO) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressDTO = addressDTO;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }
}
