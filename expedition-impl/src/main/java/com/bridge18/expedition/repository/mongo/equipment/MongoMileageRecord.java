package com.bridge18.expedition.repository.mongo.equipment;

import lombok.Data;

import java.util.Date;

@Data
public class MongoMileageRecord {
    private String miles;
    private Date takenAt;

    public MongoMileageRecord() {
    }

    public MongoMileageRecord(String miles, Date takenAt) {
        this.miles = miles;
        this.takenAt = takenAt;
    }
}
