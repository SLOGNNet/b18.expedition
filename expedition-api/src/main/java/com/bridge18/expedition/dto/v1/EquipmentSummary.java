package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.equipment.EquipmentSubType;
import com.bridge18.expedition.entities.equipment.EquipmentType;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
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
}
