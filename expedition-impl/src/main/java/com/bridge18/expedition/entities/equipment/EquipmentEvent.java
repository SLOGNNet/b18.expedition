package com.bridge18.expedition.entities.equipment;

import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.pcollections.PVector;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EquipmentEvent extends Jsonable, AggregateEvent<EquipmentEvent> {
    int NUM_SHARDS = 4;
    AggregateEventShards<EquipmentEvent> TAG = AggregateEventTag.sharded(EquipmentEvent.class, NUM_SHARDS);

    @Override
    default AggregateEventTagger<EquipmentEvent> aggregateTag() {
        return TAG;
    }

    String getEquipmentId();

    @Value
    final class EquipmentCreated implements EquipmentEvent{
        String id;

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

        Optional<PVector<MileageRecord>> mileageRecords;

        @Override
        public String getEquipmentId() {
            return id;
        }
    }

    @Value
    final class EquipmentUpdated implements EquipmentEvent{
        String id;

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

        Optional<PVector<MileageRecord>> mileageRecords;

        @Override
        public String getEquipmentId() {
            return id;
        }
    }

    @Value
    final class EquipmentDeleted implements EquipmentEvent {
        String id;

        @Override
        public String getEquipmentId() {
            return id;
        }
    }
}
