package com.bridge18.expedition.entities.equipment;

import com.fasterxml.jackson.annotation.JsonValue;


public enum EquipmentSubType {
    NONE(EquipmentType.NONE),
    TRACTOR(EquipmentType.POWER_UNIT),
    STRAIGHT_TRUCK_25(EquipmentType.POWER_UNIT),
    STRAIGHT_TRUCK_FLAT_BED(EquipmentType.POWER_UNIT),
    BUS(EquipmentType.POWER_UNIT),
    DRY_VAN_48(EquipmentType.TRAILER),
    REEFER_48(EquipmentType.TRAILER),
    FLAT_BED_48(EquipmentType.TRAILER),
    DRY_VAN_53(EquipmentType.TRAILER),
    REEFER_53(EquipmentType.TRAILER),
    FLAT_BED_53(EquipmentType.TRAILER);

    private EquipmentType parentType;

    EquipmentSubType(EquipmentType equipmentType) {
        parentType = equipmentType;
    }

    public EquipmentType getParentType() {
        return parentType;
    }

}
