package com.bridge18.expedition.entities.equipment;


import com.fasterxml.jackson.annotation.JsonValue;

public enum Ownership {
    COMPANY,
    OWNER_OPERATOR;

    @JsonValue
    public Integer toValue() {
        return  this.ordinal();
    }
}
