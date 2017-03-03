package com.bridge18.expedition.entities.contact;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize
public interface AbstractContactState extends Jsonable {

    @Value.Parameter
    String getId();

    @Value.Parameter
    Optional<String> getFirstName();

    @Value.Parameter
    Optional<String> getMiddleName();

    @Value.Parameter
    Optional<String> getLastName();

    @Value.Parameter
    Optional<String> getPosition();

    @Value.Parameter
    Optional<String> getAddress();

}
