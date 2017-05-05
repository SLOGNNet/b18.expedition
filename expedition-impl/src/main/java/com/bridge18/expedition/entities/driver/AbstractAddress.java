package com.bridge18.expedition.entities.driver;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize
public interface AbstractAddress extends Jsonable {
    @Value.Parameter
    Optional<Integer> getId();
    @Value.Parameter
    Optional<String> getName();
    @Value.Parameter
    Optional<String> getStreetAddress1();
    @Value.Parameter
    Optional<String> getStreetAddress2();
    @Value.Parameter
    Optional<String> getCity();
    @Value.Parameter
    Optional<String> getPhone();
    @Value.Parameter
    Optional<String> getState();
    @Value.Parameter
    Optional<String> getZip();
    @Value.Parameter
    Optional<String> getFax();
    @Value.Parameter
    Optional<String> getPhoneExtension();
    @Value.Parameter
    Optional<String> getFaxExtension();
    @Value.Parameter
    Optional<Double> getLatitude();
    @Value.Parameter
    Optional<Double> getLongitude();
}
