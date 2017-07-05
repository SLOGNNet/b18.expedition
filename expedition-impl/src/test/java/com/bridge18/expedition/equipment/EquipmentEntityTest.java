package com.bridge18.expedition.equipment;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import com.bridge18.expedition.dto.v1.EquipmentSummary;
import com.bridge18.expedition.entities.equipment.*;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EquipmentEntityTest {
    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("EquipmentEntityTest");
    }

    @AfterClass
    public static void teardown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testBlockingOfCommandsBeforeCreation(){
        PersistentEntityTestDriver<EquipmentCommand, EquipmentEvent, EquipmentState> persistentEntityTestDriver =
                new PersistentEntityTestDriver(system, new EquipmentEntity(), "test-equipment-2");

        GetEquipment getEquipmentCmd = GetEquipment.builder().build();
        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> getOutcome = persistentEntityTestDriver.run(getEquipmentCmd);
        assertTrue(getOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);

        UpdateEquipment updateEquipmentCmd = UpdateEquipment.builder().build();
        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> updateOutcome = persistentEntityTestDriver.run(updateEquipmentCmd);
        assertTrue(updateOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);

        DeleteEquipment deleteEquipmentCmd = DeleteEquipment.builder().build();
        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> deleteOutcome = persistentEntityTestDriver.run(deleteEquipmentCmd);
        assertTrue(deleteOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);
    }

    @Test
    public void testBlockingOfCommandsAfterDeletion(){
        PersistentEntityTestDriver<EquipmentCommand, EquipmentEvent, EquipmentState> persistentEntityTestDriver =
                new PersistentEntityTestDriver(system, new EquipmentEntity(), "test-equipment-3");

        CreateEquipment createEquipmentCmd = CreateEquipment.builder().build();
        persistentEntityTestDriver.run(createEquipmentCmd);

        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> createOutcome = persistentEntityTestDriver.run(createEquipmentCmd);
        assertTrue(createOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);


        DeleteEquipment deleteEquipmentCmd = DeleteEquipment.builder().build();
        persistentEntityTestDriver.run(deleteEquipmentCmd);


        GetEquipment getEquipmentCmd = GetEquipment.builder().build();
        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> getOutcome = persistentEntityTestDriver.run(getEquipmentCmd);
        assertTrue(getOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);

        UpdateEquipment updateEquipmentCmd = UpdateEquipment.builder().build();
        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> updateOutcome = persistentEntityTestDriver.run(updateEquipmentCmd);
        assertTrue(updateOutcome.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);

        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> createOutcome_2 = persistentEntityTestDriver.run(createEquipmentCmd);
        assertTrue(createOutcome_2.issues().get(0) instanceof PersistentEntityTestDriver.UnhandledCommand);
    }


    @Test
    public void test() {
        PersistentEntityTestDriver<EquipmentCommand, EquipmentEvent, EquipmentState> persistentEntityTestDriver =
                new PersistentEntityTestDriver(system, new EquipmentEntity(), "test-equipment-1");

        Date licensePlateExpiration = new Date();
        PVector<MileageRecord> mileageRecords = TreePVector.from(
                Arrays.asList(
                        MileageRecord.builder().miles("miles-1").takenAt(new Date()).build(),
                        MileageRecord.builder().miles("miles-2").takenAt(new Date()).build(),
                        MileageRecord.builder().miles("miles-3").takenAt(new Date()).build()
                )
        );

        CreateEquipment createEquipmentCmd = CreateEquipment.builder()
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
                .build();

        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> outcome1 = persistentEntityTestDriver.run(createEquipmentCmd);

        assertEquals(1, outcome1.events().size());

        EquipmentState equipmentState = (EquipmentState) outcome1.getReplies().get(0);
        assertEquals("test-equipment-1", ((EquipmentState) outcome1.getReplies().get(0)).getId());
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

        assertEquals(Collections.emptyList(), outcome1.issues());


        Date licensePlateExpiration2 = new Date();
        PVector<MileageRecord> mileageRecords2 = TreePVector.from(
                Arrays.asList(
                        MileageRecord.builder().miles("miles-4").takenAt(new Date()).build(),
                        MileageRecord.builder().miles("miles-5").takenAt(new Date()).build(),
                        MileageRecord.builder().miles("miles-6").takenAt(new Date()).build()
                )
        );

        UpdateEquipment updateEquipmentCmd = UpdateEquipment.builder()
                .vin("vin-2")
                .ownership(Ownership.COMPANY)
                .type(EquipmentType.NONE)
                .subType(EquipmentSubType.NONE)
                .operatingMode(OperatingMode.NONE)
                .make("make-2")
                .model("model-2")
                .colour("colour-2")
                .isSleeperBerthAvailable(false)
                .number("number-2")
                .licensePlateState("licensePlateState-2")
                .licensePlateNumber("licensePlateNumber-2")
                .licensePlateExpiration(licensePlateExpiration2)
                .notes("notes-2")
                .mileageRecords(mileageRecords2)
                .build();
        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> outcome2 = persistentEntityTestDriver.run(updateEquipmentCmd);

        assertEquals(1, outcome2.events().size());

        equipmentState = (EquipmentState) outcome2.getReplies().get(0);

        assertEquals("test-equipment-1", ((EquipmentState) outcome1.getReplies().get(0)).getId());
        assertEquals(Optional.of("vin-2"), equipmentState.getVin());
        assertEquals(Optional.of(Ownership.COMPANY), equipmentState.getOwnership());
        assertEquals(Optional.of(EquipmentType.NONE), equipmentState.getType());
        assertEquals(Optional.of(EquipmentSubType.NONE), equipmentState.getSubType());
        assertEquals(Optional.of(OperatingMode.NONE), equipmentState.getOperatingMode());
        assertEquals(Optional.of("make-2"), equipmentState.getMake());
        assertEquals(Optional.of("model-2"), equipmentState.getModel());
        assertEquals(Optional.of("colour-2"), equipmentState.getColour());
        assertEquals(Optional.of(false), equipmentState.getIsSleeperBerthAvailable());
        assertEquals(Optional.of("number-2"), equipmentState.getNumber());
        assertEquals(Optional.of("licensePlateState-2"), equipmentState.getLicensePlateState());
        assertEquals(Optional.of("licensePlateNumber-2"), equipmentState.getLicensePlateNumber());
        assertEquals(Optional.of(licensePlateExpiration2), equipmentState.getLicensePlateExpiration());
        assertEquals(Optional.of("notes-2"), equipmentState.getNotes());
        assertEquals(Optional.of(mileageRecords2), equipmentState.getMileageRecords());

        assertEquals(1, outcome2.getReplies().size());
        assertEquals(Collections.emptyList(), outcome2.issues());


        GetEquipment getEquipmentCmd = GetEquipment.builder().build();
        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> outcome3 = persistentEntityTestDriver.run(getEquipmentCmd);

        assertEquals(0, outcome3.events().size());

        equipmentState = (EquipmentState) outcome3.getReplies().get(0);

        assertEquals("test-equipment-1", ((EquipmentState) outcome1.getReplies().get(0)).getId());
        assertEquals(Optional.of("vin-2"), equipmentState.getVin());
        assertEquals(Optional.of(Ownership.COMPANY), equipmentState.getOwnership());
        assertEquals(Optional.of(EquipmentType.NONE), equipmentState.getType());
        assertEquals(Optional.of(EquipmentSubType.NONE), equipmentState.getSubType());
        assertEquals(Optional.of(OperatingMode.NONE), equipmentState.getOperatingMode());
        assertEquals(Optional.of("make-2"), equipmentState.getMake());
        assertEquals(Optional.of("model-2"), equipmentState.getModel());
        assertEquals(Optional.of("colour-2"), equipmentState.getColour());
        assertEquals(Optional.of(false), equipmentState.getIsSleeperBerthAvailable());
        assertEquals(Optional.of("number-2"), equipmentState.getNumber());
        assertEquals(Optional.of("licensePlateState-2"), equipmentState.getLicensePlateState());
        assertEquals(Optional.of("licensePlateNumber-2"), equipmentState.getLicensePlateNumber());
        assertEquals(Optional.of(licensePlateExpiration2), equipmentState.getLicensePlateExpiration());
        assertEquals(Optional.of("notes-2"), equipmentState.getNotes());
        assertEquals(Optional.of(mileageRecords2), equipmentState.getMileageRecords());

        assertEquals(1, outcome2.getReplies().size());
        assertEquals(Collections.emptyList(), outcome2.issues());


        DeleteEquipment deleteEquipmentCmd = DeleteEquipment.builder().build();
        PersistentEntityTestDriver.Outcome<EquipmentEvent, EquipmentState> outcome4 = persistentEntityTestDriver.run(deleteEquipmentCmd);
        assertEquals(1, outcome4.events().size());
        assertEquals(EquipmentDeleted.builder().id("test-equipment-1").build(),
                outcome4.events().get(0));
    }
}
