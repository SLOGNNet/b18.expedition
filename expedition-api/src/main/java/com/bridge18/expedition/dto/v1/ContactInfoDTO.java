package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.driver.ContactInfoType;

import javax.annotation.concurrent.Immutable;

@Immutable
public class ContactInfoDTO {
    public String label;

    public String value;

    public ContactInfoType type;

    public ContactInfoDTO(String label, String value, ContactInfoType type) {
        this.label = label;
        this.value = value;
        this.type = type;
    }
}
