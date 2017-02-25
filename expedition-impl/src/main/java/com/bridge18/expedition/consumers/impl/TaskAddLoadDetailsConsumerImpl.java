package com.bridge18.expedition.consumers.impl;

import com.bridge18.expedition.consumers.TaskAddLoadDetailsConsumer;
import com.bridge18.expedition.consumers.TaskNewLoadConsumer;
import com.bridge18.expedition.dto.v1.streams.TaskMessageDTO;
import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import com.bridge18.expedition.services.objects.LoadService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class TaskAddLoadDetailsConsumerImpl implements TaskAddLoadDetailsConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TaskAddLoadDetailsConsumerImpl.class);

    private LoadService loadService;

    @Inject
    public TaskAddLoadDetailsConsumerImpl(LoadService loadService) {
        this.loadService = loadService;
    }

    @Override
    public CompletionStage<TaskMessageDTO> invoke(TaskMessageDTO message) {
        LoadType loadType = null;
        FreightType freightType = null;

        if(message.variables.get("loadType") != null) {
            loadType = LoadType.valueOf(message.variables.get("loadType").toString());
        }

        if(message.variables.get("freightType") != null) {
            freightType = FreightType.valueOf(message.variables.get("freightType").toString());
        }

        return loadService.addLoadDetails(
                (String)message.variables.get("loadId"),
                Optional.ofNullable((String)message.variables.get("customerId")),
                Optional.ofNullable((String)message.variables.get("customerAddressId")),
                Optional.ofNullable((String)message.variables.get("carrierLoadNumber")),
                Optional.ofNullable((String)message.variables.get("brokerLoadNumber")),
                Optional.ofNullable(loadType),
                Optional.ofNullable(freightType)
        )
                .thenApply(loadState -> {
                    Map<String, Object> variables = new HashMap<>();

                    TaskMessageDTO dto = new TaskMessageDTO(message.requestId, null,
                            message.key,
                            message.executionId, message.activityId,
                            message.businessKey,
                            variables);

                    return dto;
                });
    }
}