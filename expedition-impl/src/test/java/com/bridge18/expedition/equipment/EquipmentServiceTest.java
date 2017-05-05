package com.bridge18.expedition.equipment;

import com.bridge18.expedition.entities.equipment.*;
import com.bridge18.expedition.services.objects.EquipmentService;
import com.bridge18.expedition.services.objects.impl.EquipmentServiceImpl;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import javax.xml.ws.WebServiceException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EquipmentServiceTest {
    private PersistentEntityRegistry persistentEntityRegistry;

    private EquipmentService equipmentService;

    @Before
    public void before() {
        persistentEntityRegistry = Mockito.mock(PersistentEntityRegistry.class);

        Mockito.doNothing().doThrow(Throwable.class).when(persistentEntityRegistry).register(EquipmentEntity.class);

        equipmentService = new EquipmentServiceImpl(persistentEntityRegistry);
    }

    @Test
    public void test() throws InterruptedException, ExecutionException, TimeoutException {
        PersistentEntityRef ref = Mockito.mock(PersistentEntityRef.class);

        Mockito.when(persistentEntityRegistry.refFor(Mockito.any(), Mockito.any())).thenReturn(ref);


        Date licensePlateExpiration = new Date();
        PVector<MileageRecord> mileageRecords = TreePVector.from(
                Arrays.asList(
                        MileageRecord.builder().miles("miles-1").takenAt(new Date()).build(),
                        MileageRecord.builder().miles("miles-2").takenAt(new Date()).build(),
                        MileageRecord.builder().miles("miles-3").takenAt(new Date()).build()
                )
        );

        Mockito.when(ref.ask(Mockito.any(CreateEquipment.class))).thenReturn(CompletableFuture.completedFuture(
                EquipmentState.builder()
                        .id("1")
                        .vin("vin-1")
                        .ownership(Ownership.COMPANY)
                        .type(EquipmentType.NONE)
                        .subType(EquipmentSubType.NONE)
                        .operatingMode(OperatingMode.NONE)
                        .make("make-1")
                        .model("model-1")
                        .colour("colour-1")
                        .isSleeperBerthAvailable(true)
                        .number("number-1")
                        .licensePlateState("licensePlateState-1")
                        .licensePlateNumber("licensePlateNumber-1")
                        .licensePlateExpiration(licensePlateExpiration)
                        .notes("notes-1")
                        .mileageRecords(mileageRecords)
                        .build()
                )
        );

        EquipmentState equipmentState = equipmentService.createEquipment(
                Optional.of("vin-1"),
                Optional.of(Ownership.COMPANY),
                Optional.of(EquipmentType.NONE),
                Optional.of(EquipmentSubType.NONE),
                Optional.of(OperatingMode.NONE),
                Optional.of("make-1"),
                Optional.of("model-1"),
                Optional.of("colour-1"),
                Optional.of(true),
                Optional.of("number-1"),
                Optional.of("licensePlateState-1"),
                Optional.of("licensePlateNumber-1"),
                Optional.of(licensePlateExpiration),
                Optional.of("notes-1"),
                Optional.of(mileageRecords)
        ).toCompletableFuture().get(5, SECONDS);

        assertNotNull(equipmentState.getId());
        assertEquals(Optional.of("vin-1"), equipmentState.getVin());
        assertEquals(Optional.of(Ownership.COMPANY), equipmentState.getOwnership());
        assertEquals(Optional.of(EquipmentType.NONE), equipmentState.getType());
        assertEquals(Optional.of(EquipmentSubType.NONE), equipmentState.getSubType());
        assertEquals(Optional.of(OperatingMode.NONE), equipmentState.getOperatingMode());
        assertEquals(Optional.of("make-1"), equipmentState.getMake());
        assertEquals(Optional.of("model-1"), equipmentState.getModel());
        assertEquals(Optional.of("colour-1"), equipmentState.getColour());
        assertEquals(Optional.of(true), equipmentState.getIsSleeperBerthAvailable());
        assertEquals(Optional.of("number-1"), equipmentState.getNumber());
        assertEquals(Optional.of("licensePlateState-1"), equipmentState.getLicensePlateState());
        assertEquals(Optional.of("licensePlateNumber-1"), equipmentState.getLicensePlateNumber());
        assertEquals(Optional.of(licensePlateExpiration), equipmentState.getLicensePlateExpiration());
        assertEquals(Optional.of("notes-1"), equipmentState.getNotes());
        assertEquals(Optional.of(mileageRecords), equipmentState.getMileageRecords());


        Mockito.when(ref.ask(Mockito.any(UpdateEquipment.class)))
                .thenReturn(CompletableFuture.completedFuture(
                        EquipmentState.builder().id("1")
                                .vin("vin-2")
                                .ownership(Ownership.COMPANY)
                                .type(EquipmentType.NONE)
                                .subType(EquipmentSubType.NONE)
                                .operatingMode(OperatingMode.NONE)
                                .make("make-2")
                                .model("model-2")
                                .colour("colour-2")
                                .isSleeperBerthAvailable(true)
                                .number("number-2")
                                .licensePlateState("licensePlateState-2")
                                .licensePlateNumber("licensePlateNumber-2")
                                .licensePlateExpiration(licensePlateExpiration)
                                .notes("notes-2")
                                .mileageRecords(mileageRecords)
                                .build()
                ));

        EquipmentState updatedEquipmentState = equipmentService
                .updateEquipment(
                        equipmentState.getId(),
                        Optional.of("vin-2"),
                        Optional.of(Ownership.COMPANY),
                        Optional.of(EquipmentType.NONE),
                        Optional.of(EquipmentSubType.NONE),
                        Optional.of(OperatingMode.NONE),
                        Optional.of("make-2"),
                        Optional.of("model-2"),
                        Optional.of("colour-2"),
                        Optional.of(true),
                        Optional.of("number-2"),
                        Optional.of("licensePlateState-2"),
                        Optional.of("licensePlateNumber-2"),
                        Optional.of(licensePlateExpiration),
                        Optional.of("notes-2"),
                        Optional.of(mileageRecords)
                )
                .toCompletableFuture().get(5, SECONDS);

        assertEquals(Optional.of("vin-2"), updatedEquipmentState.getVin());
        assertEquals(Optional.of(Ownership.COMPANY), updatedEquipmentState.getOwnership());
        assertEquals(Optional.of(EquipmentType.NONE), updatedEquipmentState.getType());
        assertEquals(Optional.of(EquipmentSubType.NONE), updatedEquipmentState.getSubType());
        assertEquals(Optional.of(OperatingMode.NONE), updatedEquipmentState.getOperatingMode());
        assertEquals(Optional.of("make-2"), updatedEquipmentState.getMake());
        assertEquals(Optional.of("model-2"), updatedEquipmentState.getModel());
        assertEquals(Optional.of("colour-2"), updatedEquipmentState.getColour());
        assertEquals(Optional.of(true), updatedEquipmentState.getIsSleeperBerthAvailable());
        assertEquals(Optional.of("number-2"), updatedEquipmentState.getNumber());
        assertEquals(Optional.of("licensePlateState-2"), updatedEquipmentState.getLicensePlateState());
        assertEquals(Optional.of("licensePlateNumber-2"), updatedEquipmentState.getLicensePlateNumber());
        assertEquals(Optional.of(licensePlateExpiration), updatedEquipmentState.getLicensePlateExpiration());
        assertEquals(Optional.of("notes-2"), updatedEquipmentState.getNotes());
        assertEquals(Optional.of(mileageRecords), updatedEquipmentState.getMileageRecords());



        Mockito.when(ref.ask(Mockito.any(GetEquipment.class)))
                .thenReturn(CompletableFuture.completedFuture(
                        EquipmentState.builder().id("1")
                                .vin("vin-2")
                                .ownership(Ownership.COMPANY)
                                .type(EquipmentType.NONE)
                                .subType(EquipmentSubType.NONE)
                                .operatingMode(OperatingMode.NONE)
                                .make("make-2")
                                .model("model-2")
                                .colour("colour-2")
                                .isSleeperBerthAvailable(true)
                                .number("number-2")
                                .licensePlateState("licensePlateState-2")
                                .licensePlateNumber("licensePlateNumber-2")
                                .licensePlateExpiration(licensePlateExpiration)
                                .notes("notes-2")
                                .mileageRecords(mileageRecords)
                                .build()
                ));

        EquipmentState getEquipmentState = equipmentService.getEquipment(
                equipmentState.getId()
        ).toCompletableFuture().get(5, SECONDS);

        assertEquals(Optional.of("vin-2"), getEquipmentState.getVin());
        assertEquals(Optional.of(Ownership.COMPANY), getEquipmentState.getOwnership());
        assertEquals(Optional.of(EquipmentType.NONE), getEquipmentState.getType());
        assertEquals(Optional.of(EquipmentSubType.NONE), getEquipmentState.getSubType());
        assertEquals(Optional.of(OperatingMode.NONE), getEquipmentState.getOperatingMode());
        assertEquals(Optional.of("make-2"), getEquipmentState.getMake());
        assertEquals(Optional.of("model-2"), getEquipmentState.getModel());
        assertEquals(Optional.of("colour-2"), getEquipmentState.getColour());
        assertEquals(Optional.of(true), getEquipmentState.getIsSleeperBerthAvailable());
        assertEquals(Optional.of("number-2"), getEquipmentState.getNumber());
        assertEquals(Optional.of("licensePlateState-2"), getEquipmentState.getLicensePlateState());
        assertEquals(Optional.of("licensePlateNumber-2"), getEquipmentState.getLicensePlateNumber());
        assertEquals(Optional.of(licensePlateExpiration), getEquipmentState.getLicensePlateExpiration());
        assertEquals(Optional.of("notes-2"), getEquipmentState.getNotes());
        assertEquals(Optional.of(mileageRecords), getEquipmentState.getMileageRecords());
    }

    @Test
    public void testWithNull() throws InterruptedException, ExecutionException, TimeoutException {
        PersistentEntityRef ref = Mockito.mock(PersistentEntityRef.class);

        Mockito.when(persistentEntityRegistry.refFor(Mockito.any(), Mockito.any())).thenReturn(ref);

        Mockito.when(ref.ask(Mockito.any(UpdateEquipment.class)))
                .thenReturn(CompletableFuture.completedFuture(
                        EquipmentState.builder()
                                .id("1")
                                .vin(Optional.empty())
                                .ownership(Ownership.COMPANY)
                                .type(EquipmentType.NONE)
                                .subType(EquipmentSubType.NONE)
                                .operatingMode(OperatingMode.NONE)
                                .make("make-2")
                                .model(Optional.empty())
                                .colour("colour-2")
                                .isSleeperBerthAvailable(true)
                                .number(Optional.empty())
                                .licensePlateState("licensePlateState-2")
                                .licensePlateNumber(Optional.empty())
                                .licensePlateExpiration(Optional.empty())
                                .notes("notes-2")
                                .mileageRecords(Optional.empty())
                                .build()
                ));

        EquipmentState updatedEquipmentState = equipmentService
                .updateEquipment(
                        "1",
                        Optional.empty(),
                        Optional.of(Ownership.COMPANY),
                        Optional.of(EquipmentType.NONE),
                        Optional.of(EquipmentSubType.NONE),
                        Optional.of(OperatingMode.NONE),
                        Optional.of("make-2"),
                        Optional.empty(),
                        Optional.of("colour-2"),
                        Optional.of(true),
                        Optional.empty(),
                        Optional.of("licensePlateState-2"),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of("notes-2"),
                        Optional.empty()
                )
                .toCompletableFuture().get(5, SECONDS);

        assertEquals(Optional.empty(), updatedEquipmentState.getVin());
        assertEquals(Optional.of(Ownership.COMPANY), updatedEquipmentState.getOwnership());
        assertEquals(Optional.of(EquipmentType.NONE), updatedEquipmentState.getType());
        assertEquals(Optional.of(EquipmentSubType.NONE), updatedEquipmentState.getSubType());
        assertEquals(Optional.of(OperatingMode.NONE), updatedEquipmentState.getOperatingMode());
        assertEquals(Optional.of("make-2"), updatedEquipmentState.getMake());
        assertEquals(Optional.empty(), updatedEquipmentState.getModel());
        assertEquals(Optional.of("colour-2"), updatedEquipmentState.getColour());
        assertEquals(Optional.of(true), updatedEquipmentState.getIsSleeperBerthAvailable());
        assertEquals(Optional.empty(), updatedEquipmentState.getNumber());
        assertEquals(Optional.of("licensePlateState-2"), updatedEquipmentState.getLicensePlateState());
        assertEquals(Optional.empty(), updatedEquipmentState.getLicensePlateNumber());
        assertEquals(Optional.empty(), updatedEquipmentState.getLicensePlateExpiration());
        assertEquals(Optional.of("notes-2"), updatedEquipmentState.getNotes());
        assertEquals(Optional.empty(), updatedEquipmentState.getMileageRecords());
    }

    @Test(expected = WebServiceException.class)
    public void testTypeValidation_1() throws InterruptedException, ExecutionException, TimeoutException {
        PersistentEntityRef ref = Mockito.mock(PersistentEntityRef.class);
        Mockito.when(persistentEntityRegistry.refFor(Mockito.any(), Mockito.any())).thenReturn(ref);

        Mockito.when(ref.ask(Mockito.any(CreateEquipment.class))).thenReturn(CompletableFuture.completedFuture(
                EquipmentState.builder()
                        .id("1")
                        .type(EquipmentType.NONE)
                        .subType(EquipmentSubType.DRY_VAN_48)
                        .build()
                )
        );
        equipmentService.createEquipment(Optional.empty(), Optional.empty(), Optional.of(EquipmentType.NONE),
                Optional.of(EquipmentSubType.DRY_VAN_48), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()).toCompletableFuture().get(5, SECONDS);
    }

    @Test(expected = WebServiceException.class)
    public void testTypeValidation_2() throws InterruptedException, ExecutionException, TimeoutException {
        PersistentEntityRef ref = Mockito.mock(PersistentEntityRef.class);
        Mockito.when(persistentEntityRegistry.refFor(Mockito.any(), Mockito.any())).thenReturn(ref);

        Mockito.when(ref.ask(Mockito.any(CreateEquipment.class))).thenReturn(CompletableFuture.completedFuture(
                EquipmentState.builder()
                        .id("1")
                        .type(EquipmentType.TRAILER)
                        .subType(EquipmentSubType.BUS)
                        .build()
                )
        );
        equipmentService.createEquipment(Optional.empty(), Optional.empty(), Optional.of(EquipmentType.TRAILER),
                Optional.of(EquipmentSubType.BUS), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()).toCompletableFuture().get(5, SECONDS);
    }
}
