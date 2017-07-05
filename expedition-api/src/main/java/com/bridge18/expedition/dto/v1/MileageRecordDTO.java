package com.bridge18.expedition.dto.v1;

import lombok.*;

import javax.annotation.concurrent.Immutable;
import java.util.Date;

@Immutable
@EqualsAndHashCode
@AllArgsConstructor
public class MileageRecordDTO {
    public String miles;

    public Date takenAt;
}
