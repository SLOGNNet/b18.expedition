package com.bridge18.expedition.services.objects;

import akka.Done;
import com.bridge18.expedition.entities.driver.*;
import org.pcollections.PVector;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface DriverService {
    CompletionStage<DriverState> createDriver(Optional<String> position,
                                              Optional<String> firstName,
                                              Optional<String> middleName,
                                              Optional<String> lastName,
                                              Optional<Date> birthDate,
                                              Optional<String> ssn,
                                              Optional<PaymentOptions> paymentOption,
                                              Optional<Double> rate,
                                              Optional<DriverTypes> driverType,
                                              Optional<PVector<ContactInfo>> contactInfo,
                                              Optional<Address> address,
                                              Optional<License> license);

    CompletionStage<DriverState> updateDriver(String id,
                                              Optional<String> position,
                                              Optional<String> firstName,
                                              Optional<String> middleName,
                                              Optional<String> lastName,
                                              Optional<Date> birthDate,
                                              Optional<String> ssn,
                                              Optional<PaymentOptions> paymentOption,
                                              Optional<Double> rate,
                                              Optional<DriverTypes> driverType,
                                              Optional<PVector<ContactInfo>> contactInfo,
                                              Optional<Address> address,
                                              Optional<License> license);

    CompletionStage<Done> deleteDriver(String id);

    CompletionStage<DriverState> getDriverInformation(String id);
}
