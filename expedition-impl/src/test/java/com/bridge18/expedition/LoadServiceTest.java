package com.bridge18.expedition;

import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import com.bridge18.expedition.entities.load.LoadCommand;
import com.bridge18.expedition.entities.load.LoadEntity;
import com.bridge18.expedition.entities.load.LoadState;
import com.bridge18.expedition.services.objects.LoadService;
import com.bridge18.expedition.services.objects.impl.LoadServiceImpl;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class LoadServiceTest {

    private PersistentEntityRegistry persistentEntityRegistry;

    private LoadService loadService;

    @Before
    public void before() {
        persistentEntityRegistry = Mockito.mock(PersistentEntityRegistry.class);

        Mockito.doNothing().doThrow(Throwable.class).when(persistentEntityRegistry).register(LoadEntity.class);

        loadService = new LoadServiceImpl(persistentEntityRegistry);
    }

    @Test
    public void test() throws InterruptedException, ExecutionException, TimeoutException {
        PersistentEntityRef ref = Mockito.mock(PersistentEntityRef.class);

        Mockito.when(persistentEntityRegistry.refFor(Mockito.any(), Mockito.any())).thenReturn(ref);

        Mockito.when(ref.ask(Mockito.any(LoadCommand.CreateLoad.class))).thenReturn(CompletableFuture.completedFuture(LoadState.builder().id("1").build()));

        LoadState loadState = loadService.createLoad().toCompletableFuture().get(5, SECONDS);

        Assert.assertNotNull(loadState.getId());
        Assert.assertTrue(loadState.getId().length() > 0);


        Mockito.when(ref.ask(Mockito.any(LoadCommand.AddLoadDetails.class)))
                .thenReturn(CompletableFuture.completedFuture(
                        LoadState.builder().id("1")
                                .customerId("customer-1")
                                .customerAddressId("customer-address-1")
                                .carrierLoadNumber("carrier-load-1")
                                .brokerLoadNumber("broker-load-1")
                                .loadType(LoadType.FULL_TRUCK_LOAD)
                                .freightType(FreightType.DRY)
                                .build()));

        LoadState updatedLoadState = loadService
                .addLoadDetails(loadState.getId(), Optional.of("customer-1"), Optional.of("customer-address-1"),
                        Optional.of("carrier-load-1"), Optional.of("broker-load-1"),
                        Optional.of(LoadType.FULL_TRUCK_LOAD), Optional.of(FreightType.DRY))
                .toCompletableFuture().get(5, SECONDS);

        Assert.assertEquals(Optional.of("customer-1"), updatedLoadState.getCustomerId());
        Assert.assertEquals(Optional.of("customer-address-1"), updatedLoadState.getCustomerAddressId());
        Assert.assertEquals(Optional.of("carrier-load-1"), updatedLoadState.getCarrierLoadNumber());
        Assert.assertEquals(Optional.of("broker-load-1"), updatedLoadState.getBrokerLoadNumber());
        Assert.assertEquals(Optional.of(LoadType.FULL_TRUCK_LOAD), updatedLoadState.getLoadType());
        Assert.assertEquals(Optional.of(FreightType.DRY), updatedLoadState.getFreightType());
    }

    @Test
    public void testWithNull() throws InterruptedException, ExecutionException, TimeoutException {
        PersistentEntityRef ref = Mockito.mock(PersistentEntityRef.class);

        Mockito.when(persistentEntityRegistry.refFor(Mockito.any(), Mockito.any())).thenReturn(ref);

        Mockito.when(ref.ask(Mockito.any(LoadCommand.AddLoadDetails.class)))
                .thenReturn(CompletableFuture.completedFuture(
                        LoadState.builder().id("1")
                                .customerId(Optional.empty())
                                .customerAddressId("customer-address-1")
                                .carrierLoadNumber("carrier-load-1")
                                .brokerLoadNumber("broker-load-1")
                                .loadType(Optional.empty())
                                .freightType(FreightType.DRY)
                                .build()));

        LoadState updatedLoadState = loadService
                .addLoadDetails("123", Optional.empty(), Optional.of("customer-address-1"),
                        Optional.of("carrier-load-1"), Optional.of("broker-load-1"),
                        Optional.empty(), Optional.of(FreightType.DRY))
                .toCompletableFuture().get(5, SECONDS);

        Assert.assertEquals(Optional.empty(), updatedLoadState.getCustomerId());
        Assert.assertEquals(Optional.of("customer-address-1"), updatedLoadState.getCustomerAddressId());
        Assert.assertEquals(Optional.of("carrier-load-1"), updatedLoadState.getCarrierLoadNumber());
        Assert.assertEquals(Optional.of("broker-load-1"), updatedLoadState.getBrokerLoadNumber());
        Assert.assertEquals(Optional.empty(), updatedLoadState.getLoadType());
        Assert.assertEquals(Optional.of(FreightType.DRY), updatedLoadState.getFreightType());
    }

}