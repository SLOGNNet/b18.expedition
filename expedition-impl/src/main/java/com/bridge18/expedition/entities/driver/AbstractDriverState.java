package com.bridge18.expedition.entities.driver;

import com.bridge18.expedition.entities.LicenseClass;
import com.bridge18.expedition.entities.PaymentOptions;
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
    Optional<Integer> getContactId();
    @Value.Parameter
    Optional<String> getPosition();
    @Value.Parameter
    Optional<String> getFirstName();
    @Value.Parameter
    Optional<String> getMiddleName();
    @Value.Parameter
    Optional<String> getLastName();
    @Value.Parameter
    Optional<String> getContactInfo();

    @Value.Parameter
    Optional<Date> getBirthDate();
    @Value.Parameter
    Optional<String> getSSN();
    @Value.Parameter
    Optional<PaymentOptions> getPaymentOptions();
    @Value.Parameter
    Optional<Double> getRate();

    @Value.Parameter
    Optional<Integer> getAddressId();
    @Value.Parameter
    Optional<String> getAddressName();
    @Value.Parameter
    Optional<String> getStreetAddress1();
    @Value.Parameter
    Optional<String> getStreetAddress2();
    @Value.Parameter
    Optional<String> getCity();
    @Value.Parameter
    Optional<String> getAddressPhone();
    @Value.Parameter
    Optional<String> getState();
    @Value.Parameter
    Optional<String> getZip();
    @Value.Parameter
    Optional<String> getAddressFax();
    @Value.Parameter
    Optional<String> getAddressPhoneExtension();
    @Value.Parameter
    Optional<String> getAddressFaxExtension();
    @Value.Parameter
    Optional<Double> getAddressLatitude();
    @Value.Parameter
    Optional<Double> getAddressLongitude();

    @Value.Parameter
    Optional<Integer> getLicenseNumber();
    @Value.Parameter
    Optional<Date> getLicenseExpiration();
    @Value.Parameter
    Optional<Date> getLicenseDateIssued();
    @Value.Parameter
    Optional<String> getLicenseStateIssue();
    @Value.Parameter
    Optional<LicenseClass> getLicenseClass();
    @Value.Parameter
    Optional<String> getLicenseEndorsements();
    @Value.Parameter
    Optional<String> getLicenseRestrictions();
}
