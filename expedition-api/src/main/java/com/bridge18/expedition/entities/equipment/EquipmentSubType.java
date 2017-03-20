package com.bridge18.expedition.entities.equipment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EquipmentSubType {
    NONE,
    TRACTOR,
    STRAIGHT_TRUCK_25,
    STRAIGHT_TRUCK_FLAT_BED,
    BUS,
    DRY_VAN_48,
    REEFER_48,
    FLAT_BED_48,
    DRY_VAN_53,
    REEFER_53,
    FLAT_BED_53;

    private EquipmentType parentType;

    EquipmentSubType(){
        parentType = this.ordinal() <= 3 ?
                EquipmentType.POWER_UNIT : EquipmentType.TRAILER;
        if(this.ordinal() == 0){
            parentType = EquipmentType.NONE;
        }
    }

    public EquipmentType getParentType() {
        return parentType;
    }

}
