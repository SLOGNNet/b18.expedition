package com.bridge18.expedition.services.objects;

import akka.Done;
import com.bridge18.expedition.entities.equipment.*;

import java.util.Date;
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
                                                    Optional<String> miles,
                                                    Optional<Date> takenAt);

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
                                                    Optional<String> miles,
                                                    Optional<Date> takenAt);

    CompletionStage<Done> deleteEquipment(String id);

    CompletionStage<EquipmentState> getEquipment(String id);
}
