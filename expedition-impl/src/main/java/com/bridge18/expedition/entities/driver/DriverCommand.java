package com.bridge18.expedition.entities.driver;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.pcollections.PVector;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface DriverCommand extends Jsonable {
    @Immutable
    @Value
    @JsonDeserialize
    final class CreateDriver implements DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState> {
        Optional<Integer> contactId;
        Optional<String> position;
        Optional<String> firstName;
        Optional<String> middleName;
        Optional<String> lastName;
        Optional<Date> birthDate;
        Optional<String> SSN;


        Optional<PaymentOptions> paymentOptions;
        Optional<Double> rate;

        Optional<PVector<ContactInfo>> contactInfo;

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

        Optional<Integer> licenseNumber;
        Optional<Date> licenseExpiration;
        Optional<Date> licenseDateIssued;
        Optional<String> licenseStateIssue;
        Optional<LicenseClass> licenseClass;
        Optional<String> licenseEndorsements;
        Optional<String> licenseRestrictions;
    }

    @Immutable
    @Value
    @JsonDeserialize
    final class UpdateDriver implements DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState>{
        Optional<Integer> contactId;
        Optional<String> position;
        Optional<String> firstName;
        Optional<String> middleName;
        Optional<String> lastName;
        Optional<Date> birthDate;
        Optional<String> SSN;


        Optional<PaymentOptions> paymentOptions;
        Optional<Double> rate;

        Optional<PVector<ContactInfo>> contactInfo;

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

        Optional<Integer> licenseNumber;
        Optional<Date> licenseExpiration;
        Optional<Date> licenseDateIssued;
        Optional<String> licenseStateIssue;
        Optional<LicenseClass> licenseClass;
        Optional<String> licenseEndorsements;
        Optional<String> licenseRestrictions;
    }

    @Immutable
    @Value
    @JsonDeserialize
    final class GetDriverInformation implements DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState> {
    }
}
