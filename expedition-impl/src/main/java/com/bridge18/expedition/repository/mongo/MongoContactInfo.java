package com.bridge18.expedition.repository.mongo;

import com.bridge18.expedition.entities.driver.ContactInfoType;
import lombok.Data;

@Data
public class MongoContactInfo {
    private String label;
    private String value;
    private ContactInfoType type;

    public MongoContactInfo() {
    }

    public MongoContactInfo(String label, String value, ContactInfoType type) {
        this.label = label;
        this.value = value;
        this.type = type;
    }
}
