package com.bridge18.expedition.entities.equipment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EquipmentSubType {
    TRACTOR(0),
    STRAIGHT_TRUCK_25(1),
    STRAIGHT_TRUCK_FLAT_BED(2),
    BUS(3),
    DRY_VAN_48(4),
    REEFER_48(5),
    FLAT_BED_48(6),
    DRY_VAN_53(7),
    REEFER_53(8),
    FLAT_BED_53(9);

    private EquipmentType parentType;

    EquipmentSubType(Integer value) {
        parentType = value <= 3 ?
                EquipmentType.POWER_UNIT : EquipmentType.TRAILER;
    }

    public EquipmentType getParentType() {
        return parentType;
    }

    @JsonValue
    public Integer toValue() {
        return  this.ordinal();
    }
}
