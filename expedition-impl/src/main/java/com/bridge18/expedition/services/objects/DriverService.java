package com.bridge18.expedition.services.objects;

import com.bridge18.expedition.entities.driver.LicenseClass;
import com.bridge18.expedition.entities.driver.PaymentOptions;
import com.bridge18.expedition.entities.driver.*;
import org.pcollections.PVector;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface DriverService {
    CompletionStage<DriverState> createDriver(Optional<String> position,
                                              Optional<String> firstName,
                                              Optional<String> middleName,
                                              Optional<String> lastName,
                                              Optional<Date> birthDate,
                                              Optional<String> ssn,
                                              Optional<PaymentOptions> paymentOptions,
                                              Optional<Double> rate,
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
                                              Optional<PaymentOptions> paymentOptions,
                                              Optional<Double> rate,
                                              Optional<PVector<ContactInfo>> contactInfo,
                                              Optional<Address> address,
                                              Optional<License> license);

    CompletionStage<DriverState> getDriverInformation(String id);
}
