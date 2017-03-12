package com.bridge18.expedition.dto.v1;

import javax.annotation.concurrent.Immutable;
import java.util.Date;

@Immutable
public class EquipmentDTO {
    public String id;

    public String vin;
    public Integer ownership;

    public Integer type;
    public Integer subType;

    public Integer operatingMode;

    public String make;
    public String model;

    public String colour;

    public Boolean isSleeperBerthAvailable;

    public String number;

    public String licensePlateState;
    public String licensePlateNumber;
    public Date licensePlateExpiration;

    public String notes;

    public MileageRecordDTO mileageRecord;

    public EquipmentDTO(String id, String vin, Integer ownership, Integer type, Integer subType, Integer operatingMode, String make, String model, String colour, Boolean isSleeperBerthAvailable, String number, String licensePlateState, String licensePlateNumber, Date licensePlateExpiration, String notes, MileageRecordDTO mileageRecord) {
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
        this.mileageRecord = mileageRecord;
    }
}
