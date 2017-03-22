package com.bridge18.expedition.entities.driver;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;
import org.pcollections.PVector;

import java.util.Date;
import java.util.Optional;


public interface DriverEvent extends Jsonable, AggregateEvent<DriverEvent> {
    int NUM_SHARDS = 4;
    AggregateEventShards<DriverEvent> TAG = AggregateEventTag.sharded(DriverEvent.class, NUM_SHARDS);

    @Override
    default AggregateEventTagger<DriverEvent> aggregateTag() {
        return TAG;
    }

    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize
    interface AbstractDriverCreated extends DriverEvent{
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
        Optional<Address> getAddress();

        @Value.Parameter
        Optional<License> getLicense();
    }

    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize
    interface AbstractDriverUpdated extends DriverEvent{
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
        Optional<Address> getAddress();

        @Value.Parameter
        Optional<License> getLicense();
    }
}
