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
        Optional<String> position;
        Optional<String> firstName;
        Optional<String> middleName;
        Optional<String> lastName;
        Optional<Date> birthDate;
        Optional<String> SSN;


        Optional<PaymentOptions> paymentOptions;
        Optional<Double> rate;

        Optional<PVector<ContactInfo>> contactInfo;

        Optional<Address> address;

        Optional<License> license;
    }

    @Immutable
    @Value
    @JsonDeserialize
    final class UpdateDriver implements DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState>{
        Optional<String> position;
        Optional<String> firstName;
        Optional<String> middleName;
        Optional<String> lastName;
        Optional<Date> birthDate;
        Optional<String> SSN;


        Optional<PaymentOptions> paymentOptions;
        Optional<Double> rate;

        Optional<PVector<ContactInfo>> contactInfo;

        Optional<Address> address;

        Optional<License> license;
    }

    @Immutable
    @Value
    @JsonDeserialize
    final class GetDriverInformation implements DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState> {
    }
}
