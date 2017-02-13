package com.bridge18.expedition.entities.load;

import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * The state for the {@link LoadEntity} entity.
 */
@Value.Immutable
@ImmutableStyle
@JsonDeserialize
public interface AbstractLoadState extends Jsonable {
    @Value.Parameter
    String getId();

    @Value.Parameter
    Optional<String> getCustomerId();

    @Value.Parameter
    Optional<String> getCustomerAddressId();

    @Value.Parameter
    Optional<String> getCarrierLoadNumber();

    @Value.Parameter
    Optional<String> getBrokerLoadNumber();

    @Value.Parameter
    Optional<LoadType> getLoadType();

    @Value.Parameter
    Optional<FreightType> getFreightType();
}
