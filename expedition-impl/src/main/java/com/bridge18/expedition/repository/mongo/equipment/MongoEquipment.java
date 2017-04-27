package com.bridge18.expedition.repository.mongo.equipment;

import com.bridge18.expedition.entities.equipment.EquipmentSubType;
import com.bridge18.expedition.entities.equipment.EquipmentType;
import com.bridge18.expedition.entities.equipment.OperatingMode;
import com.bridge18.expedition.entities.equipment.Ownership;
import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class MongoEquipment {
    @Id
    private String id;

    private String equipmentId;
    private String vin;
    private Ownership ownership;

    private EquipmentType type;
    private EquipmentSubType subType;

    private OperatingMode operatingMode;

    private String make;
    private String model;

    private String colour;

    private Boolean isSleeperBerthAvailable;

    private String number;

    private String licensePlateState;
    private String licensePlateNumber;
    private Date licensePlateExpiration;

    private String notes;

    private List<MongoMileageRecord> mileageRecords;

    public MongoEquipment() {
    }

    public MongoEquipment(String equipmentId, String vin, Ownership ownership, EquipmentType type, EquipmentSubType subType, OperatingMode operatingMode, String make, String model, String colour, Boolean isSleeperBerthAvailable, String number, String licensePlateState, String licensePlateNumber, Date licensePlateExpiration, String notes, List<MongoMileageRecord> mileageRecords) {
        this.equipmentId = equipmentId;
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
        this.mileageRecords = mileageRecords;
    }
}
