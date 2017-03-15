package com.bridge18.expedition.dto.v1;

import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class EquipmentSummary {
    String id;
    String vin;
    Integer type;
    Integer subType;

    public EquipmentSummary(String id, String vin, Integer type, Integer subType) {
        this.id = id;
        this.vin = vin;
        this.type = type;
        this.subType = subType;
    }
}
