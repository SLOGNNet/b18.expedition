package com.bridge18.expedition;

import com.bridge18.expedition.api.LagomDriverService;
import com.bridge18.expedition.api.LagomEquipmentService;
import com.bridge18.expedition.api.LagomLoadService;
import com.bridge18.expedition.api.LagomSwaggerService;
import com.bridge18.expedition.consumers.TaskAddLoadDetailsConsumer;
import com.bridge18.expedition.consumers.TaskNewLoadConsumer;
import com.bridge18.expedition.consumers.impl.TaskAddLoadDetailsConsumerImpl;
import com.bridge18.expedition.consumers.impl.TaskNewLoadConsumerImpl;
import com.bridge18.expedition.repository.DriverRepository;
import com.bridge18.expedition.repository.mongo.driver.MongoDriverRepository;
import com.bridge18.expedition.services.lagom.LagomDriverServiceImpl;
import com.bridge18.expedition.services.lagom.LagomEquipmentServiceImpl;
import com.bridge18.expedition.services.lagom.LagomLoadServiceImpl;
import com.bridge18.expedition.services.lagom.LagomSwaggerServiceImpl;
import com.bridge18.expedition.services.objects.DriverService;
import com.bridge18.expedition.services.objects.EquipmentService;
import com.bridge18.expedition.services.objects.LoadService;
import com.bridge18.expedition.services.objects.impl.DriverServiceImpl;
import com.bridge18.expedition.services.objects.impl.EquipmentServiceImpl;
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
        bind(DriverService.class).to(DriverServiceImpl.class);
        bind(EquipmentService.class).to(EquipmentServiceImpl.class);
        bind(DriverRepository.class).to(MongoDriverRepository.class);

        bindServices(serviceBinding(LagomLoadService.class, LagomLoadServiceImpl.class),
                serviceBinding(LagomDriverService.class, LagomDriverServiceImpl.class),
                serviceBinding(LagomEquipmentService.class, LagomEquipmentServiceImpl.class),
                serviceBinding(LagomSwaggerService.class, LagomSwaggerServiceImpl.class));

        bind(TaskNewLoadConsumer.class).to(TaskNewLoadConsumerImpl.class);
        bind(TaskAddLoadDetailsConsumer.class).to(TaskAddLoadDetailsConsumerImpl.class);

        //@TOOD: reactivate module
//        bind(ConsumersBootstrap.class).asEagerSingleton();
    }
}
