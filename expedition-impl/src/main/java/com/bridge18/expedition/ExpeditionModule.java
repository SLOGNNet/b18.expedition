package com.bridge18.expedition;

import com.bridge18.expedition.api.LagomLoadService;
import com.bridge18.expedition.services.lagom.LagomLoadServiceImpl;
import com.bridge18.expedition.services.objects.LoadService;
import com.bridge18.expedition.services.objects.impl.LoadServiceImpl;
import com.bridge18.expedition.worker.WorkerBootstrap;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class ExpeditionModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bind(LoadService.class).to(LoadServiceImpl.class);

        bindServices(serviceBinding(LagomLoadService.class, LagomLoadServiceImpl.class));
    }
}
