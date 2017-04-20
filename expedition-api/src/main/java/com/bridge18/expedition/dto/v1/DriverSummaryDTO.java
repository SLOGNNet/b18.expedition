package com.bridge18.expedition.dto.v1;

import java.util.List;

public final class DriverSummaryDTO {
    String id;
    String firstName;
    String lastName;
    List<ContactInfoDTO> contactInfo;

    public DriverSummaryDTO(String id, String firstName, String lastName, List<ContactInfoDTO> contactInfo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactInfo = contactInfo;
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

    public List<ContactInfoDTO> getContactInfo() {
        return contactInfo;
    }
}
