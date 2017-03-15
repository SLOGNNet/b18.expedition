package com.bridge18.expedition.entities.equipment;

import akka.Done;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EquipmentCommand extends Jsonable {
    @Value
    @JsonDeserialize
    final class CreateEquipment implements EquipmentCommand, CompressedJsonable, PersistentEntity.ReplyType<EquipmentState>{
        Optional<String> vin;
        Optional<Ownership> ownership;

        Optional<EquipmentType> type;
        Optional<EquipmentSubType> subType;

        Optional<OperatingMode> operatingMode;

        Optional<String> make;
        Optional<String> model;

        Optional<String> colour;

        Optional<Boolean> isSleeperBerthAvailable;

        Optional<String> number;

        Optional<String> licensePlateState;
        Optional<String> licensePlateNumber;
        Optional<Date> licensePlateExpiration;

        Optional<String> notes;

        Optional<List<MileageRecord>> mileageRecords;
    }

    @Value
    @JsonDeserialize
    final class ChangeEquipment implements EquipmentCommand, CompressedJsonable, PersistentEntity.ReplyType<EquipmentState>{
        Optional<String> vin;
        Optional<Ownership> ownership;

        Optional<EquipmentType> type;
        Optional<EquipmentSubType> subType;

        Optional<OperatingMode> operatingMode;

        Optional<String> make;
        Optional<String> model;

        Optional<String> colour;

        Optional<Boolean> isSleeperBerthAvailable;

        Optional<String> number;

        Optional<String> licensePlateState;
        Optional<String> licensePlateNumber;
        Optional<Date> licensePlateExpiration;

        Optional<String> notes;

        Optional<List<MileageRecord>> mileageRecords;
    }

    @Value
    @JsonDeserialize
    final class DeleteEquipment implements EquipmentCommand, CompressedJsonable, PersistentEntity.ReplyType<Done>{
    }

    @Value
    @JsonDeserialize
    final class GetEquipment implements EquipmentCommand, CompressedJsonable, PersistentEntity.ReplyType<EquipmentState> {
    }
}
