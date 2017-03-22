package com.bridge18.expedition.entities.driver;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;
import org.pcollections.PVector;

import java.util.Date;
import java.util.Optional;


public interface DriverCommand extends Jsonable {
    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize
    interface AbstractCreateDriver extends DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState> {
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
        Optional<Address> getAddress();

        @Value.Parameter
        Optional<License> getLicense();
    }

    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize
    interface AbstractUpdateDriver extends DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState>{
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
        Optional<Address> getAddress();

        @Value.Parameter
        Optional<License> getLicense();
    }

    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize
    interface AbstractGetDriverInformation extends DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState> {
    }
}
