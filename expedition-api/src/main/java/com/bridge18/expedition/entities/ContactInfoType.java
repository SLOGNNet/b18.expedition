package com.bridge18.expedition.entities;


import com.fasterxml.jackson.annotation.JsonValue;

public enum ContactInfoType {
    NONE,
    PHONE,
    FAX,
    EMAIL;

    @JsonValue
    public Integer toValue() {
        return  this.ordinal();
    }
}
