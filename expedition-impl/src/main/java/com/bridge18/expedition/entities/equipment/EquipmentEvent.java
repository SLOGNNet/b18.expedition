package com.bridge18.expedition.entities.equipment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;
import org.pcollections.PVector;

import java.util.Date;
import java.util.Optional;

public interface EquipmentEvent extends Jsonable, AggregateEvent<EquipmentEvent> {
    int NUM_SHARDS = 4;
    AggregateEventShards<EquipmentEvent> TAG = AggregateEventTag.sharded(EquipmentEvent.class, NUM_SHARDS);

    @Override
    default AggregateEventTagger<EquipmentEvent> aggregateTag() {
        return TAG;
    }

    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize
    interface AbstractEquipmentCreated extends EquipmentEvent{
        @Value.Parameter
        String getId();

        @Value.Parameter
        Optional<String> getVin();
        @Value.Parameter
        Optional<Ownership> getOwnership();
        @Value.Parameter
        Optional<EquipmentType> getType();
        @Value.Parameter
        Optional<EquipmentSubType> getSubType();
        @Value.Parameter
        Optional<OperatingMode> getOperatingMode();
        @Value.Parameter
        Optional<String> getMake();
        @Value.Parameter
        Optional<String> getModel();
        @Value.Parameter
        Optional<String> getColour();
        @Value.Parameter
        Optional<Boolean> getIsSleeperBerthAvailable();
        @Value.Parameter
        Optional<String> getNumber();
        @Value.Parameter
        Optional<String> getLicensePlateState();
        @Value.Parameter
        Optional<String> getLicensePlateNumber();
        @Value.Parameter
        Optional<Date> getLicensePlateExpiration();
        @Value.Parameter
        Optional<String> getNotes();
        @Value.Parameter
        Optional<PVector<MileageRecord>> getMileageRecords();

    }

    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize
    interface AbstractEquipmentUpdated extends EquipmentEvent{
        @Value.Parameter
        String getId();

        @Value.Parameter
        Optional<String> getVin();
        @Value.Parameter
        Optional<Ownership> getOwnership();
        @Value.Parameter
        Optional<EquipmentType> getType();
        @Value.Parameter
        Optional<EquipmentSubType> getSubType();
        @Value.Parameter
        Optional<OperatingMode> getOperatingMode();
        @Value.Parameter
        Optional<String> getMake();
        @Value.Parameter
        Optional<String> getModel();
        @Value.Parameter
        Optional<String> getColour();
        @Value.Parameter
        Optional<Boolean> getIsSleeperBerthAvailable();
        @Value.Parameter
        Optional<String> getNumber();
        @Value.Parameter
        Optional<String> getLicensePlateState();
        @Value.Parameter
        Optional<String> getLicensePlateNumber();
        @Value.Parameter
        Optional<Date> getLicensePlateExpiration();
        @Value.Parameter
        Optional<String> getNotes();
        @Value.Parameter
        Optional<PVector<MileageRecord>> getMileageRecords();

    }

    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize
    interface AbstractEquipmentDeleted extends EquipmentEvent {
        @Value.Parameter
        String getId();

    }
}
