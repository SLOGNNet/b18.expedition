package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.equipment.EquipmentSubType;
import com.bridge18.expedition.entities.equipment.EquipmentType;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EquipmentSummary {
    String id;
    String vin;
    EquipmentType type;
    EquipmentSubType subType;

    public EquipmentSummary(String id, String vin, EquipmentType type, EquipmentSubType subType) {
        this.id = id;
        this.vin = vin;
        this.type = type;
        this.subType = subType;
    }

    public String getId() {
        return id;
    }

    public String getVin() {
        return vin;
    }

    public EquipmentType getType() {
        return type;
    }

    public EquipmentSubType getSubType() {
        return subType;
    }
}
