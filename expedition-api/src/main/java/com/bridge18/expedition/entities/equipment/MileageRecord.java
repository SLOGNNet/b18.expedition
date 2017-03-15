package com.bridge18.expedition.entities.equipment;

import lombok.Value;

import java.util.Date;
import java.util.Optional;

@Value
public class MileageRecord {
    Optional<String> miles;

    Optional<Date> takenAt;
}
