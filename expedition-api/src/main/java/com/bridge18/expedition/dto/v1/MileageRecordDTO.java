package com.bridge18.expedition.dto.v1;

import javax.annotation.concurrent.Immutable;
import java.util.Date;

@Immutable
public class MileageRecordDTO {
    public String miles;

    public Date takenAt;

    public MileageRecordDTO(String miles, Date takenAt) {
        this.miles = miles;
        this.takenAt = takenAt;
    }
}
