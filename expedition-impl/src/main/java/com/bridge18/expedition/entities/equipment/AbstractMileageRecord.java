package com.bridge18.expedition.entities.equipment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;

import java.util.Date;
import java.util.Optional;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize
public interface AbstractMileageRecord extends Jsonable {
    @Value.Parameter
    Optional<String> getMiles();

    @Value.Parameter
    Optional<Date> getTakenAt();
}
