package com.bridge18.expedition.entities.equipment;

import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.Optional;

public interface EquipmentEvent extends Jsonable, AggregateEvent<EquipmentEvent> {
    int NUM_SHARDS = 4;
    AggregateEventShards<EquipmentEvent> TAG = AggregateEventTag.sharded(EquipmentEvent.class, NUM_SHARDS);

    @Override
    default AggregateEventTagger<EquipmentEvent> aggregateTag() {
        return TAG;
    }

    String getEquipmentId();

    @Immutable
    final class EquipmentCreated implements EquipmentEvent{
        public String id;

        public Optional<String> vin;
        public Optional<Ownership> ownership;

        public Optional<EquipmentType> type;
        public Optional<EquipmentSubType> subType;

        public Optional<OperatingMode> operatingMode;

        public Optional<String> make;
        public Optional<String> model;

        public Optional<String> colour;

        public Optional<Boolean> isSleeperBerthAvailable;

        public Optional<String> number;

        public Optional<String> licensePlateState;
        public Optional<String> licensePlateNumber;
        public Optional<Date> licensePlateExpiration;

        public Optional<String> notes;

        public Optional<String> miles;
        public Optional<Date> takenAt;

        @Override
        public String getEquipmentId() {
            return id;
        }

        public EquipmentCreated(String id, Optional<String> vin, Optional<Ownership> ownership, Optional<EquipmentType> type, Optional<EquipmentSubType> subType, Optional<OperatingMode> operatingMode, Optional<String> make, Optional<String> model, Optional<String> colour, Optional<Boolean> isSleeperBerthAvailable, Optional<String> number, Optional<String> licensePlateState, Optional<String> licensePlateNumber, Optional<Date> licensePlateExpiration, Optional<String> notes, Optional<String> miles, Optional<Date> takenAt) {
            this.id = id;
            this.vin = vin;
            this.ownership = ownership;
            this.type = type;
            this.subType = subType;
            this.operatingMode = operatingMode;
            this.make = make;
            this.model = model;
            this.colour = colour;
            this.isSleeperBerthAvailable = isSleeperBerthAvailable;
            this.number = number;
            this.licensePlateState = licensePlateState;
            this.licensePlateNumber = licensePlateNumber;
            this.licensePlateExpiration = licensePlateExpiration;
            this.notes = notes;
            this.miles = miles;
            this.takenAt = takenAt;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            EquipmentCreated that = (EquipmentCreated) o;

            return new EqualsBuilder()
                    .append(id, that.id)
                    .append(vin, that.vin)
                    .append(ownership, that.ownership)
                    .append(type, that.type)
                    .append(subType, that.subType)
                    .append(operatingMode, that.operatingMode)
                    .append(make, that.make)
                    .append(model, that.model)
                    .append(colour, that.colour)
                    .append(isSleeperBerthAvailable, that.isSleeperBerthAvailable)
                    .append(number, that.number)
                    .append(licensePlateState, that.licensePlateState)
                    .append(licensePlateNumber, that.licensePlateNumber)
                    .append(licensePlateExpiration, that.licensePlateExpiration)
                    .append(notes, that.notes)
                    .append(miles, that.miles)
                    .append(takenAt, that.takenAt)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(id)
                    .append(vin)
                    .append(ownership)
                    .append(type)
                    .append(subType)
                    .append(operatingMode)
                    .append(make)
                    .append(model)
                    .append(colour)
                    .append(isSleeperBerthAvailable)
                    .append(number)
                    .append(licensePlateState)
                    .append(licensePlateNumber)
                    .append(licensePlateExpiration)
                    .append(notes)
                    .append(miles)
                    .append(takenAt)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("vin", vin)
                    .append("ownership", ownership)
                    .append("type", type)
                    .append("subType", subType)
                    .append("operatingMode", operatingMode)
                    .append("make", make)
                    .append("model", model)
                    .append("colour", colour)
                    .append("isSleeperBerthAvailable", isSleeperBerthAvailable)
                    .append("number", number)
                    .append("licensePlateState", licensePlateState)
                    .append("licensePlateNumber", licensePlateNumber)
                    .append("licensePlateExpiration", licensePlateExpiration)
                    .append("notes", notes)
                    .append("miles", miles)
                    .append("takenAt", takenAt)
                    .toString();
        }
    }

    @Immutable
    final class EquipmentChanged implements EquipmentEvent{
        public String id;

        public Optional<String> vin;
        public Optional<Ownership> ownership;

        public Optional<EquipmentType> type;
        public Optional<EquipmentSubType> subType;

        public Optional<OperatingMode> operatingMode;

        public Optional<String> make;
        public Optional<String> model;

        public Optional<String> colour;

        public Optional<Boolean> isSleeperBerthAvailable;

        public Optional<String> number;

        public Optional<String> licensePlateState;
        public Optional<String> licensePlateNumber;
        public Optional<Date> licensePlateExpiration;

        public Optional<String> notes;

        public Optional<String> miles;
        public Optional<Date> takenAt;

        @Override
        public String getEquipmentId() {
            return id;
        }

        public EquipmentChanged(String id, Optional<String> vin, Optional<Ownership> ownership, Optional<EquipmentType> type, Optional<EquipmentSubType> subType, Optional<OperatingMode> operatingMode, Optional<String> make, Optional<String> model, Optional<String> colour, Optional<Boolean> isSleeperBerthAvailable, Optional<String> number, Optional<String> licensePlateState, Optional<String> licensePlateNumber, Optional<Date> licensePlateExpiration, Optional<String> notes, Optional<String> miles, Optional<Date> takenAt) {
            this.id = id;
            this.vin = vin;
            this.ownership = ownership;
            this.type = type;
            this.subType = subType;
            this.operatingMode = operatingMode;
            this.make = make;
            this.model = model;
            this.colour = colour;
            this.isSleeperBerthAvailable = isSleeperBerthAvailable;
            this.number = number;
            this.licensePlateState = licensePlateState;
            this.licensePlateNumber = licensePlateNumber;
            this.licensePlateExpiration = licensePlateExpiration;
            this.notes = notes;
            this.miles = miles;
            this.takenAt = takenAt;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            EquipmentChanged that = (EquipmentChanged) o;

            return new EqualsBuilder()
                    .append(id, that.id)
                    .append(vin, that.vin)
                    .append(ownership, that.ownership)
                    .append(type, that.type)
                    .append(subType, that.subType)
                    .append(operatingMode, that.operatingMode)
                    .append(make, that.make)
                    .append(model, that.model)
                    .append(colour, that.colour)
                    .append(isSleeperBerthAvailable, that.isSleeperBerthAvailable)
                    .append(number, that.number)
                    .append(licensePlateState, that.licensePlateState)
                    .append(licensePlateNumber, that.licensePlateNumber)
                    .append(licensePlateExpiration, that.licensePlateExpiration)
                    .append(notes, that.notes)
                    .append(miles, that.miles)
                    .append(takenAt, that.takenAt)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(id)
                    .append(vin)
                    .append(ownership)
                    .append(type)
                    .append(subType)
                    .append(operatingMode)
                    .append(make)
                    .append(model)
                    .append(colour)
                    .append(isSleeperBerthAvailable)
                    .append(number)
                    .append(licensePlateState)
                    .append(licensePlateNumber)
                    .append(licensePlateExpiration)
                    .append(notes)
                    .append(miles)
                    .append(takenAt)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("vin", vin)
                    .append("ownership", ownership)
                    .append("type", type)
                    .append("subType", subType)
                    .append("operatingMode", operatingMode)
                    .append("make", make)
                    .append("model", model)
                    .append("colour", colour)
                    .append("isSleeperBerthAvailable", isSleeperBerthAvailable)
                    .append("number", number)
                    .append("licensePlateState", licensePlateState)
                    .append("licensePlateNumber", licensePlateNumber)
                    .append("licensePlateExpiration", licensePlateExpiration)
                    .append("notes", notes)
                    .append("miles", miles)
                    .append("takenAt", takenAt)
                    .toString();
        }
    }

    @Immutable
    final class EquipmentDeleted implements EquipmentEvent{
        String id;

        @Override
        public String getEquipmentId() {
            return id;
        }

        public EquipmentDeleted(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            EquipmentDeleted that = (EquipmentDeleted) o;

            return new EqualsBuilder()
                    .append(id, that.id)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(id)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .toString();
        }
    }
}
