package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import lombok.*;
import javax.annotation.concurrent.Immutable;

@Immutable
@EqualsAndHashCode
@AllArgsConstructor
public class LoadDTO {
    public String id;

    public String customerId;
    public String customerAddressId;
    public String carrierLoadNumber;
    public String brokerLoadNumber;
    public LoadType loadType;
    public FreightType freightType;

    public LoadDTO(String id) {
        this.id = id;
    }
}