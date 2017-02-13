package com.bridge18.expedition;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Optional;

import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import com.bridge18.expedition.entities.load.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver.Outcome;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import com.bridge18.expedition.entities.load.LoadEvent.LoadDetailsAdded;

public class LoadEntityTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("LoadEntityTest");
    }

    @AfterClass
    public static void teardown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void test() {
        PersistentEntityTestDriver<LoadCommand, LoadEvent, LoadState> driver =
                new PersistentEntityTestDriver(system, new LoadEntity(), "load-1");

        Outcome<LoadEvent, LoadState> outcome1 = driver.run(new LoadCommand.CreateLoad());
        assertEquals("load-1", ((LoadState) outcome1.getReplies().get(0)).getId());
        assertEquals(Collections.emptyList(), outcome1.issues());

        Outcome<LoadEvent, LoadState> outcome2 = driver.run(
                new LoadCommand.AddLoadDetails(Optional.of("customer-1"), Optional.of("address-1"),
                        Optional.of("carrier-load-1"), Optional.of("broker-load-1"),
                        Optional.of(LoadType.FULL_TRUCK_LOAD), Optional.of(FreightType.DRY)));

        assertEquals(1, outcome2.events().size());
        assertEquals(new LoadDetailsAdded(Optional.of("customer-1"), Optional.of("address-1"),
                        Optional.of("carrier-load-1"), Optional.of("broker-load-1"),
                        Optional.of(LoadType.FULL_TRUCK_LOAD), Optional.of(FreightType.DRY)),
                outcome2.events().get(0));


        assertEquals(Optional.of("customer-1"), outcome2.state().getCustomerId());
        assertEquals(Optional.of("address-1"), outcome2.state().getCustomerAddressId());
        assertEquals(Optional.of("carrier-load-1"), outcome2.state().getCarrierLoadNumber());
        assertEquals(Optional.of("broker-load-1"), outcome2.state().getBrokerLoadNumber());
        assertEquals(Optional.of(LoadType.FULL_TRUCK_LOAD), outcome2.state().getLoadType());
        assertEquals(Optional.of(FreightType.DRY), outcome2.state().getFreightType());

        LoadState loadState = (LoadState) outcome2.getReplies().get(0);

        assertEquals(Optional.of("customer-1"), loadState.getCustomerId());
        assertEquals(Optional.of("address-1"), loadState.getCustomerAddressId());
        assertEquals(Optional.of("carrier-load-1"), loadState.getCarrierLoadNumber());
        assertEquals(Optional.of("broker-load-1"), loadState.getBrokerLoadNumber());
        assertEquals(Optional.of(LoadType.FULL_TRUCK_LOAD), loadState.getLoadType());
        assertEquals(Optional.of(FreightType.DRY), loadState.getFreightType());

        assertEquals(1, outcome2.getReplies().size());
        assertEquals(Collections.emptyList(), outcome2.issues());
    }

}