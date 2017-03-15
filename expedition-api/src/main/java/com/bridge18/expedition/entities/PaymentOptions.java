package com.bridge18.expedition.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentOptions {
    NONE,
    PER_MILE,
    PERCENTAGE,
    HOURLY,
    FLAT;

    @JsonValue
    public Integer toValue() {
        return  this.ordinal();
    }
}
