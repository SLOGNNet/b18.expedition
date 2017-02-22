package com.bridge18.expedition.dto.v1.streams;

import com.bridge18.dto.streams.StreamMessageDTO;

import java.util.Map;

public class TaskMessageDTO extends StreamMessageDTO {
    public final String executionId;
    public final String activityId;
    public final String businessKey;

    public final Map<String, Object> variables;

    public TaskMessageDTO(String requestId, String callbackTopic, String key,
                          String executionId, String activityId, String businessKey,
                          Map<String, Object> variables) {
        super(requestId, callbackTopic, key);

        this.executionId = executionId;
        this.activityId = activityId;
        this.businessKey = businessKey;

        this.variables = variables;
    }
}