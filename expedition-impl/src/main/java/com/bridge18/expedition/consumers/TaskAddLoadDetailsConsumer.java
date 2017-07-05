package com.bridge18.expedition.consumers;

import com.bridge18.expedition.dto.v1.streams.TaskMessageDTO;
import com.bridge18.service.streams.ConsumerAsync;

public interface TaskAddLoadDetailsConsumer extends ConsumerAsync<TaskMessageDTO, TaskMessageDTO> {

}