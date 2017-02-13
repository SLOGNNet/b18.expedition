package com.bridge18.expedition.services.objects.impl;

import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import com.bridge18.expedition.entities.load.LoadEntity;
import com.bridge18.expedition.entities.load.LoadCommand;
import com.bridge18.expedition.entities.load.LoadState;
import com.bridge18.expedition.services.objects.LoadService;
import com.google.inject.Singleton;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Singleton
public class LoadServiceImpl implements LoadService {
    private final PersistentEntityRegistry persistentEntityRegistry;

    @Inject
    public LoadServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        persistentEntityRegistry.register(LoadEntity.class);
    }

    @Override
    public CompletionStage<LoadState> createLoad() {
        PersistentEntityRef<LoadCommand> ref = persistentEntityRegistry.refFor(LoadEntity.class, UUID.randomUUID().toString());

        LoadCommand.CreateLoad cmd = new LoadCommand.CreateLoad();

        return ref.ask(cmd);
    }

    @Override
    public CompletionStage<LoadState> addLoadDetails(String id,
                                                     Optional<String> customerId,
                                                     Optional<String> customerAddressId,
                                                     Optional<String> carrierLoadNumber,
                                                     Optional<String> brokerLoadNumber,
                                                     Optional<LoadType> loadType,
                                                     Optional<FreightType> freightType) {

        LoadCommand.AddLoadDetails cmd = new LoadCommand.AddLoadDetails(
                customerId, customerAddressId,
                carrierLoadNumber, brokerLoadNumber,
                loadType, freightType);

        PersistentEntityRef<LoadCommand> ref =
                persistentEntityRegistry.refFor(LoadEntity.class, id);

        return ref.ask(cmd);
    }
}