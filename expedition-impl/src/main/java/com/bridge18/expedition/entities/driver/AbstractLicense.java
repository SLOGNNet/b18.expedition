package com.bridge18.expedition.entities.driver;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;

import java.util.Date;
import java.util.Optional;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize
public interface AbstractLicense extends Jsonable {
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
