package com.bridge18.expedition;

import com.bridge18.expedition.api.LagomLoadService;
import com.bridge18.expedition.services.lagom.LagomLoadServiceImpl;
import com.bridge18.expedition.services.objects.LoadService;
import com.bridge18.expedition.services.objects.impl.LoadServiceImpl;
import com.bridge18.expedition.worker.WorkerBootstrap;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.api.ConfigurationServiceLocator;
import com.lightbend.lagom.javadsl.api.ServiceLocator;
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

        if (environment.isProd()) {
            bind(ServiceLocator.class).to(ConfigurationServiceLocator.class);
        }
    }
}
