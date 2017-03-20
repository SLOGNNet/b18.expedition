package com.bridge18.expedition.entities.driver;

import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.pcollections.PVector;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface DriverEvent extends Jsonable, AggregateEvent<DriverEvent> {
    int NUM_SHARDS = 4;
    AggregateEventShards<DriverEvent> TAG = AggregateEventTag.sharded(DriverEvent.class, NUM_SHARDS);

    @Override
    default AggregateEventTagger<DriverEvent> aggregateTag() {
        return TAG;
    }

    @Immutable
    @Value
    final class DriverCreated implements DriverEvent{
        String driverId;

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
    final class DriverUpdated implements DriverEvent{
        String driverId;

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
}
