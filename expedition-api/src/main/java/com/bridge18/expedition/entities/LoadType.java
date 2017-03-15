package com.bridge18.expedition.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LoadType {
    FULL_TRUCK_LOAD,
    LESS_THAN_TRUCK;

    @JsonValue
    public Integer toValue() {
        return  this.ordinal();
    }
}
