package com.bridge18.expedition.equipment;

import akka.actor.ActorSystem;
import com.bridge18.expedition.api.LagomEquipmentService;
import com.bridge18.expedition.dto.v1.EquipmentDTO;
import com.bridge18.expedition.dto.v1.MileageRecordDTO;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.equipment.*;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import javax.xml.ws.WebServiceException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LagomEquipmentServiceTest {
    static ActorSystem system;

    private final static ServiceTest.Setup setup = defaultSetup().withCassandra(true)
            .configureBuilder(b ->
                    b.configure("cassandra-query-journal.eventual-consistency-delay", "0")
            );

    private static ServiceTest.TestServer testServer;
    private static LagomEquipmentService testService;


    @BeforeClass
    public static void beforeAll() {
        system = ActorSystem.create("LagomEquipmentServiceTest");

        testServer = ServiceTest.startServer(setup);
        testService = testServer.client(LagomEquipmentService.class);
    }

    @Test
    public void test() throws InterruptedException, ExecutionException, TimeoutException {
        Date licensePlateExpiration = new Date();
        List<MileageRecordDTO> mileageRecords = Arrays.asList(
                new MileageRecordDTO("miles-1", new Date()),
                new MileageRecordDTO("miles-2", new Date()),
                new MileageRecordDTO("miles-3", new Date())
        );
        EquipmentDTO inputEquipmentDTO = new EquipmentDTO(null, "vin-1", Ownership.COMPANY, EquipmentType.NONE,
                EquipmentSubType.NONE, OperatingMode.NONE, "make-1", "model-1", "colour-1", true,
                "number-1", "licensePlateState-1", "licensePlateNumber-1",
                licensePlateExpiration, "notes-1", mileageRecords);
        EquipmentDTO createdEquipmentDTO = testService.createNewEquipment().invoke(inputEquipmentDTO)
                .toCompletableFuture().get(10, SECONDS);

        assertNotNull(createdEquipmentDTO.id);
        assertEquals("vin-1", createdEquipmentDTO.vin);
        assertEquals(Ownership.COMPANY, createdEquipmentDTO.ownership);
        assertEquals(EquipmentType.NONE, createdEquipmentDTO.type);
        assertEquals(EquipmentSubType.NONE, createdEquipmentDTO.subType);
        assertEquals(OperatingMode.NONE, createdEquipmentDTO.operatingMode);
        assertEquals("make-1", createdEquipmentDTO.make);
        assertEquals("model-1", createdEquipmentDTO.model);
        assertEquals("colour-1", createdEquipmentDTO.colour);
        assertEquals(true, createdEquipmentDTO.isSleeperBerthAvailable);
        assertEquals("number-1", createdEquipmentDTO.number);
        assertEquals("licensePlateState-1", createdEquipmentDTO.licensePlateState);
        assertEquals("licensePlateNumber-1", createdEquipmentDTO.licensePlateNumber);
        assertEquals(licensePlateExpiration, createdEquipmentDTO.licensePlateExpiration);
        assertEquals("notes-1", createdEquipmentDTO.notes);
        assertEquals(mileageRecords, createdEquipmentDTO.mileageRecords);

        inputEquipmentDTO = new EquipmentDTO(null, "vin-2", Ownership.COMPANY, EquipmentType.NONE,
                EquipmentSubType.NONE, OperatingMode.NONE, "make-2", "model-2", "colour-2", true,
                "number-2", "licensePlateState-2", "licensePlateNumber-2",
                licensePlateExpiration, "notes-2", mileageRecords);
        EquipmentDTO updatedEquipmentDTO = testService.updateEquipmentInformation(createdEquipmentDTO.id)
                .invoke(inputEquipmentDTO).toCompletableFuture().get(10, SECONDS);

        assertNotNull(updatedEquipmentDTO.id);
        assertEquals("vin-2", updatedEquipmentDTO.vin);
        assertEquals(Ownership.COMPANY, updatedEquipmentDTO.ownership);
        assertEquals(EquipmentType.NONE, updatedEquipmentDTO.type);
        assertEquals(EquipmentSubType.NONE, updatedEquipmentDTO.subType);
        assertEquals(OperatingMode.NONE, updatedEquipmentDTO.operatingMode);
        assertEquals("make-2", updatedEquipmentDTO.make);
        assertEquals("model-2", updatedEquipmentDTO.model);
        assertEquals("colour-2", updatedEquipmentDTO.colour);
        assertEquals(true, updatedEquipmentDTO.isSleeperBerthAvailable);
        assertEquals("number-2", updatedEquipmentDTO.number);
        assertEquals("licensePlateState-2", updatedEquipmentDTO.licensePlateState);
        assertEquals("licensePlateNumber-2", updatedEquipmentDTO.licensePlateNumber);
        assertEquals(licensePlateExpiration, updatedEquipmentDTO.licensePlateExpiration);
        assertEquals("notes-2", updatedEquipmentDTO.notes);
        assertEquals(mileageRecords, updatedEquipmentDTO.mileageRecords);

        EquipmentDTO getEquipmentDTO = testService.getEquipment(createdEquipmentDTO.id)
                .invoke().toCompletableFuture().get(10, SECONDS);
        assertNotNull(getEquipmentDTO.id);
        assertEquals("vin-2", getEquipmentDTO.vin);
        assertEquals(Ownership.COMPANY, getEquipmentDTO.ownership);
        assertEquals(EquipmentType.NONE, getEquipmentDTO.type);
        assertEquals(EquipmentSubType.NONE, getEquipmentDTO.subType);
        assertEquals(OperatingMode.NONE, getEquipmentDTO.operatingMode);
        assertEquals("make-2", getEquipmentDTO.make);
        assertEquals("model-2", getEquipmentDTO.model);
        assertEquals("colour-2", getEquipmentDTO.colour);
        assertEquals(true, getEquipmentDTO.isSleeperBerthAvailable);
        assertEquals("number-2", getEquipmentDTO.number);
        assertEquals("licensePlateState-2", getEquipmentDTO.licensePlateState);
        assertEquals("licensePlateNumber-2", getEquipmentDTO.licensePlateNumber);
        assertEquals(licensePlateExpiration, getEquipmentDTO.licensePlateExpiration);
        assertEquals("notes-2", getEquipmentDTO.notes);
        assertEquals(mileageRecords, getEquipmentDTO.mileageRecords);
    }

    @Test(expected = ExecutionException.class)
    public void testTypeValidation() throws InterruptedException, ExecutionException, TimeoutException {
        EquipmentDTO inputEquipmentDTO = new EquipmentDTO(null, "vin-1", Ownership.COMPANY, null,
                EquipmentSubType.NONE, OperatingMode.NONE, "make-1", "model-1", "colour-1", true,
                "number-1", "licensePlateState-1", "licensePlateNumber-1",
                null, "notes-1", null);
        testService.createNewEquipment().invoke(inputEquipmentDTO)
                .toCompletableFuture().get(10, SECONDS);
    }

    @Test(expected = ExecutionException.class)
    public void testSubTypeValidation() throws InterruptedException, ExecutionException, TimeoutException {
        EquipmentDTO inputEquipmentDTO = new EquipmentDTO(null, "vin-1", Ownership.COMPANY, EquipmentType.TRAILER,
                null, OperatingMode.NONE, "make-1", "model-1", "colour-1", true,
                "number-1", "licensePlateState-1", "licensePlateNumber-1",
                null, "notes-1", null);
        testService.createNewEquipment().invoke(inputEquipmentDTO)
                .toCompletableFuture().get(10, SECONDS);
    }
}
