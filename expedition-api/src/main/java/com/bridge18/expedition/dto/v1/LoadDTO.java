package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import lombok.EqualsAndHashCode;

import javax.annotation.concurrent.Immutable;

@Immutable
@EqualsAndHashCode
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

    public LoadDTO(String id, String customerId, String customerAddressId, String carrierLoadNumber, String brokerLoadNumber, LoadType loadType, FreightType freightType) {
        this.id = id;
        this.customerId = customerId;
        this.customerAddressId = customerAddressId;
        this.carrierLoadNumber = carrierLoadNumber;
        this.brokerLoadNumber = brokerLoadNumber;
        this.loadType = loadType;
        this.freightType = freightType;
    }
}