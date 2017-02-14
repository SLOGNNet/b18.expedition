/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.bridge18.expedition.services.lagom;

import akka.NotUsed;
import com.bridge18.expedition.api.LagomLoadService;
import com.bridge18.expedition.dto.v1.LoadDTO;
import com.bridge18.expedition.services.objects.LoadService;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;
import java.util.Optional;

public class LagomLoadServiceImpl implements LagomLoadService {

    private LoadService loadService;

    @Inject
    public LagomLoadServiceImpl(LoadService loadService) {
        this.loadService = loadService;
    }

    @Override
    public ServiceCall<NotUsed, LoadDTO> createNewLoad() {
        return request -> {
            return loadService.createLoad()
                    .thenApply((loadState) ->
                            new LoadDTO(loadState.getId()));
        };
    }

    @Override
    public ServiceCall<LoadDTO, LoadDTO> addLoadDetails(String id) {
        return request -> {
            return loadService.addLoadDetails(id,
                    Optional.ofNullable(request.customerId), Optional.ofNullable(request.customerAddressId),
                    Optional.ofNullable(request.carrierLoadNumber), Optional.ofNullable(request.brokerLoadNumber),
                    Optional.ofNullable(request.loadType), Optional.ofNullable(request.freightType))

                    .thenApply(loadState ->
                            new LoadDTO(loadState.getId(),
                                    loadState.getCustomerId().orElse(null), loadState.getCustomerAddressId().orElse(null),
                                    loadState.getCarrierLoadNumber().orElse(null), loadState.getBrokerLoadNumber().orElse(null),
                                    loadState.getLoadType().orElse(null), loadState.getFreightType().orElse(null)));
        };
    }
}