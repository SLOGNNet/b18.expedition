package com.bridge18.expedition.consumers.impl;

import com.bridge18.expedition.consumers.TaskNewLoadConsumer;
import com.bridge18.expedition.dto.v1.streams.TaskMessageDTO;
import com.bridge18.expedition.services.objects.LoadService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class TaskNewLoadConsumerImpl implements TaskNewLoadConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TaskNewLoadConsumerImpl.class);

    private LoadService loadService;

    @Inject
    public TaskNewLoadConsumerImpl(LoadService loadService) {
        this.loadService = loadService;
    }

    @Override
    public CompletionStage<TaskMessageDTO> invoke(TaskMessageDTO message) {
        return loadService.createLoad()
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