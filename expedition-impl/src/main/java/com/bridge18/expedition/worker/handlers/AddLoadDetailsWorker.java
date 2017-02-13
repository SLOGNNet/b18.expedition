package com.bridge18.expedition.worker.handlers;

import com.bridge18.expedition.services.objects.LoadService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.ext.sdk.TaskContext;
import org.camunda.bpm.ext.sdk.Worker;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class AddLoadDetailsWorker implements Worker {
    private LoadService loadService;

    @Inject
    public AddLoadDetailsWorker(LoadService loadService) {
        this.loadService = loadService;
    }

    @Override
    public void doWork(TaskContext taskContext) {
        VariableMap variables = taskContext.getVariables();

//        variables

        loadService.createLoad()
            .thenAccept(loadState -> {
                Map<String, Object> variablesMap = new HashMap<>();

                variablesMap.put("loadId", loadState.getId());

                taskContext.complete(variablesMap);
            });
    }
}