package com.bridge18.expedition;

import com.bridge18.expedition.api.LagomLoadService;
import com.bridge18.expedition.consumers.ConsumersBootstrap;
import com.bridge18.expedition.consumers.TaskAddLoadDetailsConsumer;
import com.bridge18.expedition.consumers.TaskNewLoadConsumer;
import com.bridge18.expedition.consumers.impl.TaskAddLoadDetailsConsumerImpl;
import com.bridge18.expedition.consumers.impl.TaskNewLoadConsumerImpl;
import com.bridge18.expedition.services.lagom.LagomLoadServiceImpl;
import com.bridge18.expedition.services.objects.LoadService;
import com.bridge18.expedition.services.objects.impl.LoadServiceImpl;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import play.Configuration;
import play.Environment;

public class ExpeditionModule extends AbstractModule implements ServiceGuiceSupport {
    private final Environment environment;
    private final Configuration configuration;

    public ExpeditionModule(Environment environment, Configuration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(LoadService.class).to(LoadServiceImpl.class);

        bindServices(serviceBinding(LagomLoadService.class, LagomLoadServiceImpl.class));

        bind(TaskNewLoadConsumer.class).to(TaskNewLoadConsumerImpl.class);
        bind(TaskAddLoadDetailsConsumer.class).to(TaskAddLoadDetailsConsumerImpl.class);

        bind(ConsumersBootstrap.class).asEagerSingleton();
    }
}
