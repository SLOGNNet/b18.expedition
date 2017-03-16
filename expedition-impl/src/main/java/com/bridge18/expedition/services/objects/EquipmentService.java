package com.bridge18.expedition.services.objects;

import akka.Done;
import com.bridge18.expedition.entities.equipment.*;
import org.pcollections.PVector;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface EquipmentService {
    CompletionStage<EquipmentState> createEquipment(Optional<String> vin,
                                                    Optional<Ownership> ownership,
                                                    Optional<EquipmentType> type,
                                                    Optional<EquipmentSubType> subType,
                                                    Optional<OperatingMode> operatingMode,
                                                    Optional<String> make,
                                                    Optional<String> model,
                                                    Optional<String> colour,
                                                    Optional<Boolean> isSleeperBerthAvailable,
                                                    Optional<String> number,
                                                    Optional<String> licensePlateState,
                                                    Optional<String> licensePlateNumber,
                                                    Optional<Date> licensePlateExpiration,
                                                    Optional<String> notes,
                                                    Optional<PVector<MileageRecord>> mileageRecords);

    CompletionStage<EquipmentState> changeEquipment(String id,
                                                    Optional<String> vin,
                                                    Optional<Ownership> ownership,
                                                    Optional<EquipmentType> type,
                                                    Optional<EquipmentSubType> subType,
                                                    Optional<OperatingMode> operatingMode,
                                                    Optional<String> make,
                                                    Optional<String> model,
                                                    Optional<String> colour,
                                                    Optional<Boolean> isSleeperBerthAvailable,
                                                    Optional<String> number,
                                                    Optional<String> licensePlateState,
                                                    Optional<String> licensePlateNumber,
                                                    Optional<Date> licensePlateExpiration,
                                                    Optional<String> notes,
                                                    Optional<PVector<MileageRecord>> mileageRecords);

    CompletionStage<Done> deleteEquipment(String id);

    CompletionStage<EquipmentState> getEquipment(String id);
}
