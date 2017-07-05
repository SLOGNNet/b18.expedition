package com.bridge18.expedition.entities.equipment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;
import org.pcollections.PVector;

import java.util.Date;
import java.util.Optional;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize
public interface AbstractEquipmentState extends Jsonable {
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
