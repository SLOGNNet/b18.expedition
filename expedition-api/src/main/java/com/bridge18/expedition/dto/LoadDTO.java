package com.bridge18.expedition.dto;

import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
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