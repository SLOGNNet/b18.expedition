package com.bridge18.expedition.entities.driver;

import lombok.Value;

import javax.annotation.concurrent.Immutable;
import java.util.Optional;

@Value
@Immutable
public class Address {
    Optional<Integer> addressId;
    Optional<String> addressName;
    Optional<String> streetAddress1;
    Optional<String> streetAddress2;
    Optional<String> city;
    Optional<String> addressPhone;
    Optional<String> state;
    Optional<String> zip;
    Optional<String> addressFax;
    Optional<String> addressPhoneExtension;
    Optional<String> addressFaxExtension;
    Optional<Double> addressLatitude;
    Optional<Double> addressLongitude;
}
