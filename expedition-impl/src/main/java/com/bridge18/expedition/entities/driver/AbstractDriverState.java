package com.bridge18.expedition.entities.driver;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;

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
    Optional<Boolean> getIsActive();

    @Value.Parameter
    Optional<String> getFirstName();
    @Value.Parameter
    Optional<String> getLastName();
    @Value.Parameter
    Optional<Date> getBirthDate();
    @Value.Parameter
    Optional<String> getSSN();

    @Value.Parameter
    Optional<DriverType> getDriverType();
    @Value.Parameter
    Optional<Date> getHireDate();
    @Value.Parameter
    Optional<Date> getTerminationDate();
    @Value.Parameter
    Optional<EmploymentStatus> getStatus();

    @Value.Parameter
    Optional<PaymentOptions> getPaymentOptions();
    @Value.Parameter
    Optional<Double> getRate();

    @Value.Parameter
    Optional<String> getPrimaryPhone();
    @Value.Parameter
    Optional<PhoneType> getPrimaryPhoneType();
    @Value.Parameter
    Optional<String> getAltPhone();
    @Value.Parameter
    Optional<PhoneType> getAltPhoneType();
    @Value.Parameter
    Optional<String> getFax();
    @Value.Parameter
    Optional<String> getEmail();

    @Value.Parameter
    Optional<String> getStreetAddress();
    @Value.Parameter
    Optional<String> getSecondStreetAddress();
    @Value.Parameter
    Optional<String> getCity();
    @Value.Parameter
    Optional<String> getState();
    @Value.Parameter
    Optional<String> getZip();

    @Value.Parameter
    Optional<String> getLicenseNumber();
    @Value.Parameter
    Optional<Date> getLicenseExpiration();
    @Value.Parameter
    Optional<Date> getLicenseDateIssued();
    @Value.Parameter
    Optional<LicenseStateIssue> getLicenseStateIssue();
    @Value.Parameter
    Optional<LicenseClass> getLicenseClass();
    @Value.Parameter
    Optional<LicenseEndorsements> getLicenseEndorsements();
}
