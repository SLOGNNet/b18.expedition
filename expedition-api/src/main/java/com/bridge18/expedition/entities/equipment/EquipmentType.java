package com.bridge18.expedition.entities.equipment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EquipmentType {
    POWER_UNIT,
    TRAILER;

    @JsonValue
    public Integer toValue() {
        return  this.ordinal();
    }
}
