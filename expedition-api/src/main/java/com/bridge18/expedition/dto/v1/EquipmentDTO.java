package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.equipment.*;
import org.pcollections.PVector;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.List;

@Immutable
public class EquipmentDTO {
    public String id;

    public String vin;
    public Ownership ownership;

    public EquipmentType type;
    public EquipmentSubType subType;

    public OperatingMode operatingMode;

    public String make;
    public String model;

    public String colour;

    public Boolean isSleeperBerthAvailable;

    public String number;

    public String licensePlateState;
    public String licensePlateNumber;
    public Date licensePlateExpiration;

    public String notes;

    public PVector<MileageRecord> mileageRecords;

    public EquipmentDTO(String id, String vin, Ownership ownership, EquipmentType type, EquipmentSubType subType, OperatingMode operatingMode, String make, String model, String colour, Boolean isSleeperBerthAvailable, String number, String licensePlateState, String licensePlateNumber, Date licensePlateExpiration, String notes, PVector<MileageRecord> mileageRecord) {
        this.id = id;
        this.vin = vin;
        this.ownership = ownership;
        this.type = type;
        this.subType = subType;
        this.operatingMode = operatingMode;
        this.make = make;
        this.model = model;
        this.colour = colour;
        this.isSleeperBerthAvailable = isSleeperBerthAvailable;
        this.number = number;
        this.licensePlateState = licensePlateState;
        this.licensePlateNumber = licensePlateNumber;
        this.licensePlateExpiration = licensePlateExpiration;
        this.notes = notes;
        this.mileageRecords = mileageRecord;
    }
}
