package com.bridge18.expedition.entities.driver;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;
import org.pcollections.PVector;

import java.util.Date;
import java.util.Optional;

/**
 * The state for the {@link DriverState} entity.
 */
@Value.Immutable
@ImmutableStyle
@JsonDeserialize
public interface AbstractDriverState extends Jsonable {
    @Value.Parameter
    String getId();


    @Value.Parameter
    Optional<String> getPosition();
    @Value.Parameter
    Optional<String> getFirstName();
    @Value.Parameter
    Optional<String> getMiddleName();
    @Value.Parameter
    Optional<String> getLastName();
    @Value.Parameter
    Optional<PVector<ContactInfo>> getContactInfo();

    @Value.Parameter
    Optional<Date> getBirthDate();
    @Value.Parameter
    Optional<String> getSsn();
    @Value.Parameter
    Optional<PaymentOptions> getPaymentOption();
    @Value.Parameter
    Optional<Double> getRate();

    @Value.Parameter
    Optional<DriverTypes> getDriverType();

    @Value.Parameter
    Optional<Address> getAddress();

    @Value.Parameter
    Optional<License> getLicense();
}
