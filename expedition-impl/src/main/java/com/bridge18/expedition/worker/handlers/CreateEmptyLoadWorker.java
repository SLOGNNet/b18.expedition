package com.bridge18.expedition.worker.handlers;

import com.bridge18.expedition.services.objects.LoadService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.camunda.bpm.ext.sdk.TaskContext;
import org.camunda.bpm.ext.sdk.Worker;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CreateEmptyLoadWorker implements Worker {
    private LoadService loadService;

    @Inject
    public CreateEmptyLoadWorker(LoadService loadService) {
        this.loadService = loadService;
    }

    @Override
    public void doWork(TaskContext taskContext) {
        loadService.createLoad()
            .thenAccept(loadEntity -> {
                Map<String, Object> variables = new HashMap<>();

                variables.put("loadId", loadEntity.getId());

                taskContext.complete(variables);
            });
    }
}