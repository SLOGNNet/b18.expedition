package com.bridge18.expedition.dto.v1;

import java.util.Date;

public class MileageRecordDTO {
    public String miles;
    public Date takenAt;

    public MileageRecordDTO() {
    }

    public MileageRecordDTO(String miles, Date takenAt) {
        this.miles = miles;
        this.takenAt = takenAt;
    }
}
