package com.bridge18.expedition.dto.v1;

import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public final class DriverSummary {
    String id;
    String firstName;
    String lastName;

    public DriverSummary(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
