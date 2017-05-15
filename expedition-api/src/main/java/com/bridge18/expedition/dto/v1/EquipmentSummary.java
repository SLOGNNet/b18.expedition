package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.equipment.EquipmentSubType;
import com.bridge18.expedition.entities.equipment.EquipmentType;
import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class EquipmentSummary {
    String id;
    String vin;
    EquipmentType type;
    EquipmentSubType subType;
}
