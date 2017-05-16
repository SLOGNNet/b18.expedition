package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.equipment.EquipmentSubType;
import com.bridge18.expedition.entities.equipment.EquipmentType;
import com.bridge18.expedition.entities.equipment.OperatingMode;
import com.bridge18.expedition.entities.equipment.Ownership;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Immutable
@EqualsAndHashCode
@AllArgsConstructor
public class EquipmentDTO {
    public String id;

    public String vin;
    public Ownership ownership;

    @Constraints.Required(message = "Equipment type can't be empty")
    public EquipmentType type;

    @Constraints.Required(message = "Equipment subtype can't be empty")
    public EquipmentSubType subType;

    public OperatingMode operatingMode;

    public String make;
    public String model;

    public String colour;

    public Boolean isSleeperBerthAvailable;

    @Constraints.Required(message = "Number can't be empty")
    public String number;

    public String licensePlateState;
    public String licensePlateNumber;
    public Date licensePlateExpiration;

    public String notes;

    public List<MileageRecordDTO> mileageRecords;


    public List<ValidationError> validate() {
        final List<ValidationError> errors = new ArrayList<>();
        if (this.number != null) {
            errors.add(new ValidationError("number == null", "number == null"));
        }
        return errors.size() > 0 ? errors : null;
    }
}
