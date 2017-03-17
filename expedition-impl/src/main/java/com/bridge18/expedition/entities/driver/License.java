package com.bridge18.expedition.entities.driver;

import lombok.Value;
import lombok.experimental.Wither;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.Optional;

@Value
@Wither
@Immutable
public class License {
    Optional<Integer> licenseNumber;
    Optional<Date> licenseExpiration;
    Optional<Date> licenseDateIssued;
    Optional<String> licenseStateIssue;
    Optional<LicenseClass> licenseClass;
    Optional<String> licenseEndorsements;
    Optional<String> licenseRestrictions;
}
