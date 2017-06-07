package com.bridge18.expedition.dto.v1;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public final class DriverSummaryDTO {
    String id;
    String firstName;
    String lastName;
    AddressDTO addressDTO;
}
