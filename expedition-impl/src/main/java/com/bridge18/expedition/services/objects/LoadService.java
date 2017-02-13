package com.bridge18.expedition.services.objects;

import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import com.bridge18.expedition.entities.load.LoadState;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface LoadService {
    CompletionStage<LoadState> createLoad();
    CompletionStage<LoadState> addLoadDetails(String id,
                                              Optional<String> customerId,
                                              Optional<String> customerAddressId,
                                              Optional<String> carrierLoadNumber,
                                              Optional<String> brokerLoadNumber,
                                              Optional<LoadType> loadType,
                                              Optional<FreightType> freightType);
}