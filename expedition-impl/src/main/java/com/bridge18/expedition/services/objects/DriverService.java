package com.bridge18.expedition.services.objects;

import akka.Done;
import com.bridge18.expedition.entities.LicenseClass;
import com.bridge18.expedition.entities.PaymentOptions;
import com.bridge18.expedition.entities.driver.*;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * Created by Viktor on 01.03.2017.
 */
public interface DriverService {
    CompletionStage<DriverState> createDriver(Optional<Integer> contactId,
                                              Optional<String> position,
                                              Optional<String> firstName,
                                              Optional<String> middleName,
                                              Optional<String> lastName,
                                              Optional<Date> birthDate,
                                              Optional<String> SSN,
                                              Optional<PaymentOptions> paymentOptions,
                                              Optional<Double> rate,
                                              Optional<String> contactInfo,
                                              Optional<Integer> addressId,
                                              Optional<String> addressName,
                                              Optional<String> streetAddress1,
                                              Optional<String> streetAddress2,
                                              Optional<String> city,
                                              Optional<String> addressPhone,
                                              Optional<String> state,
                                              Optional<String> zip,
                                              Optional<String> addressFax,
                                              Optional<String> addressPhoneExtension,
                                              Optional<String> addressFaxExtension,
                                              Optional<Double> addressLatitude,
                                              Optional<Double> addressLongitude,
                                              Optional<Integer> licenseNumber,
                                              Optional<Date> licenseExpiration,
                                              Optional<Date> licenseDateIssued,
                                              Optional<String> licenseStateIssue,
                                              Optional<LicenseClass> licenseClass,
                                              Optional<String> licenseEndorsements,
                                              Optional<String> licenseRestrictions);

    CompletionStage<DriverState> changeDriverInformation(String id,
                                                         Optional<Integer> contactId,
                                                          Optional<String> position,
                                                          Optional<String> firstName,
                                                          Optional<String> middleName,
                                                          Optional<String> lastName,
                                                          Optional<Date> birthDate,
                                                          Optional<String> SSN,
                                                          Optional<PaymentOptions> paymentOptions,
                                                          Optional<Double> rate,
                                                          Optional<String> contactInfo,
                                                          Optional<Integer> addressId,
                                                          Optional<String> addressName,
                                                          Optional<String> streetAddress1,
                                                          Optional<String> streetAddress2,
                                                          Optional<String> city,
                                                          Optional<String> addressPhone,
                                                          Optional<String> state,
                                                          Optional<String> zip,
                                                          Optional<String> addressFax,
                                                          Optional<String> addressPhoneExtension,
                                                          Optional<String> addressFaxExtension,
                                                          Optional<Double> addressLatitude,
                                                          Optional<Double> addressLongitude,
                                                          Optional<Integer> licenseNumber,
                                                          Optional<Date> licenseExpiration,
                                                          Optional<Date> licenseDateIssued,
                                                          Optional<String> licenseStateIssue,
                                                          Optional<LicenseClass> licenseClass,
                                                          Optional<String> licenseEndorsements,
                                                          Optional<String> licenseRestrictions);

    CompletionStage<DriverState> getDriverInformation(String id);
}
