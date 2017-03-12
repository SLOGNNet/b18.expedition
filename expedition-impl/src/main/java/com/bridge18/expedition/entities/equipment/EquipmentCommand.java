package com.bridge18.expedition.entities.equipment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.Optional;

public interface EquipmentCommand extends Jsonable {
    @Immutable
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

        Optional<String> miles;
        Optional<Date> takenAt;

        public CreateEquipment() {
        }

        public CreateEquipment(Optional<String> vin, Optional<Ownership> ownership, Optional<EquipmentType> type, Optional<EquipmentSubType> subType, Optional<OperatingMode> operatingMode, Optional<String> make, Optional<String> model, Optional<String> colour, Optional<Boolean> isSleeperBerthAvailable, Optional<String> number, Optional<String> licensePlateState, Optional<String> licensePlateNumber, Optional<Date> licensePlateExpiration, Optional<String> notes, Optional<String> miles, Optional<Date> takenAt) {
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

            CreateEquipment that = (CreateEquipment) o;

            return new EqualsBuilder()
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
    }

    @Immutable
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

        Optional<String> miles;
        Optional<Date> takenAt;

        public ChangeEquipment() {
        }

        public ChangeEquipment(Optional<String> vin, Optional<Ownership> ownership, Optional<EquipmentType> type, Optional<EquipmentSubType> subType, Optional<OperatingMode> operatingMode, Optional<String> make, Optional<String> model, Optional<String> colour, Optional<Boolean> isSleeperBerthAvailable, Optional<String> number, Optional<String> licensePlateState, Optional<String> licensePlateNumber, Optional<Date> licensePlateExpiration, Optional<String> notes, Optional<String> miles, Optional<Date> takenAt) {
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

            ChangeEquipment that = (ChangeEquipment) o;

            return new EqualsBuilder()
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
    }

    @Immutable
    @JsonDeserialize
    final class GetEquipment implements EquipmentCommand, CompressedJsonable, PersistentEntity.ReplyType<EquipmentState> {

        public GetEquipment() {
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof GetEquipment) {
                return true;
            }
            return super.equals(obj);
        }
    }
}
