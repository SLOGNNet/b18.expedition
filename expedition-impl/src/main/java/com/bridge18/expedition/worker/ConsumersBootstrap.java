package com.bridge18.expedition.worker;

import com.bridge18.expedition.worker.handlers.CreateEmptyLoadWorker;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.camunda.bpm.ext.sdk.CamundaClient;
import org.camunda.bpm.ext.sdk.Worker;
import org.camunda.bpm.ext.sdk.WorkerRegistration;
import play.Configuration;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ConsumersBootstrap {
    private List<WorkerRegistration> registrations = new ArrayList<>();

    private CamundaClient camundaClient;
    private Configuration configuration;

    @Inject
    public ConsumersBootstrap(Configuration configuration, CreateEmptyLoadWorker createEmptyLoad) {
        this.configuration = configuration;

        camundaClient = CamundaClient.create()
                .endpointUrl(configuration.getString("camunda-endpoint-url"))
                .build();

        register("Load.createEmpty", createEmptyLoad, 3000);
    }

    protected void register(String topic, Worker worker, int lockTime) {
        WorkerRegistration registration = camundaClient.registerWorker()
                .topicName(topic)
                .lockTime(lockTime)
                .worker(worker)
                .build();

        registrations.add(registration);
    }
}