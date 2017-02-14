package com.bridge18.expedition;

import akka.actor.ActorSystem;
import com.bridge18.expedition.api.LagomLoadService;
import com.bridge18.expedition.dto.v1.LoadDTO;
import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static java.util.concurrent.TimeUnit.SECONDS;

public class LagomLoadServiceTest {

    static ActorSystem system;

    private final static ServiceTest.Setup setup = defaultSetup().withCassandra(true)
            .configureBuilder(b ->
                    // by default, cassandra-query-journal delays propagation of events by 10sec. In test we're using
                    // a 1 node cluster so this delay is not necessary.
                    b.configure("cassandra-query-journal.eventual-consistency-delay", "0")
            );

    private static ServiceTest.TestServer testServer;
    private static LagomLoadService testService;


    @BeforeClass
    public static void beforeAll() {
        system = ActorSystem.create("LagomLoadServiceTest");

        testServer = ServiceTest.startServer(setup);
        testService = testServer.client(LagomLoadService.class);
    }

    @Test
    public void test() throws InterruptedException, ExecutionException, TimeoutException {
        LoadDTO loadDTO = testService.createNewLoad().invoke().toCompletableFuture().get(5, SECONDS);

        Assert.assertNotNull(loadDTO.id);
        Assert.assertTrue(loadDTO.id.length() > 0);

        LoadDTO updatedLoadDTO = testService
                .addLoadDetails(loadDTO.id).invoke(new LoadDTO(null, "customer-1", "customer-address-1",
                "carrier-load-1", "broker-load-1", LoadType.FULL_TRUCK_LOAD, FreightType.DRY))
                .toCompletableFuture().get(5, SECONDS);

        Assert.assertEquals("customer-1", updatedLoadDTO.customerId);
        Assert.assertEquals("customer-address-1", updatedLoadDTO.customerAddressId);
        Assert.assertEquals("carrier-load-1", updatedLoadDTO.carrierLoadNumber);
        Assert.assertEquals("broker-load-1", updatedLoadDTO.brokerLoadNumber);
        Assert.assertEquals(LoadType.FULL_TRUCK_LOAD, updatedLoadDTO.loadType);
        Assert.assertEquals(FreightType.DRY, updatedLoadDTO.freightType);
    }

    @Test
    public void testWithNull() throws InterruptedException, ExecutionException, TimeoutException {
        LoadDTO loadDTO = testService.createNewLoad().invoke().toCompletableFuture().get(5, SECONDS);

        Assert.assertNotNull(loadDTO.id);
        Assert.assertTrue(loadDTO.id.length() > 0);

        LoadDTO updatedLoadDTO = testService.addLoadDetails(loadDTO.id)
                .invoke(new LoadDTO(null, "customer-1", null,
                        "carrier-load-1", "broker-load-1", null, FreightType.DRY))
                .toCompletableFuture().get(5, SECONDS);

        Assert.assertEquals("customer-1", updatedLoadDTO.customerId);
        Assert.assertEquals(null, updatedLoadDTO.customerAddressId);
        Assert.assertEquals("carrier-load-1", updatedLoadDTO.carrierLoadNumber);
        Assert.assertEquals("broker-load-1", updatedLoadDTO.brokerLoadNumber);
        Assert.assertEquals(null, updatedLoadDTO.loadType);
        Assert.assertEquals(FreightType.DRY, updatedLoadDTO.freightType);
    }

}