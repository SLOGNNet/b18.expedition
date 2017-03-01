package com.bridge18.expedition.services.lagom;

import akka.Done;
import akka.NotUsed;
import com.bridge18.expedition.api.LagomDriverService;
import com.bridge18.expedition.dto.v1.DisableDriverDTO;
import com.bridge18.expedition.dto.v1.DriverDTO;
import com.bridge18.expedition.services.objects.DriverService;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by Viktor on 01.03.2017.
 */
public class LagomDriverServiceImpl implements LagomDriverService {
    private DriverService driverService;

    @Inject
    public LagomDriverServiceImpl(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    public ServiceCall<DriverDTO, DriverDTO> createNewDriver() {
        return request ->
                driverService.createDriver(Optional.ofNullable(request.firstName),
                Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                Optional.ofNullable(request.SSN), Optional.ofNullable(request.driverType),
                Optional.ofNullable(request.hireDate), Optional.ofNullable(request.terminationDate),
                Optional.ofNullable(request.status), Optional.ofNullable(request.paymentOptions),
                Optional.ofNullable(request.rate), Optional.ofNullable(request.primaryPhone),
                Optional.ofNullable(request.primaryPhoneType), Optional.ofNullable(request.altPhone),
                Optional.ofNullable(request.altPhoneType), Optional.ofNullable(request.fax),
                Optional.ofNullable(request.email), Optional.ofNullable(request.streetAddress),
                Optional.ofNullable(request.secondStreetAddress), Optional.ofNullable(request.city),
                Optional.ofNullable(request.state), Optional.ofNullable(request.zip),
                Optional.ofNullable(request.licenseNumber), Optional.ofNullable(request.licenseExpiration),
                Optional.ofNullable(request.licenseDateIssued), Optional.ofNullable(request.licenseStateIssue),
                Optional.ofNullable(request.licenseClass), Optional.ofNullable(request.licenseEndorsements))

                .thenApply(driverState ->
                        new DriverDTO(driverState.getId(),
                                driverState.getIsActive().orElse(false),
                                driverState.getFirstName().orElse(null),
                                driverState.getLastName().orElse(null),
                                driverState.getBirthDate().orElse(null),
                                driverState.getSSN().orElse(null),
                                driverState.getDriverType().orElse(null),
                                driverState.getHireDate().orElse(null),
                                driverState.getTerminationDate().orElse(null),
                                driverState.getStatus().orElse(null),
                                driverState.getPaymentOptions().orElse(null),
                                driverState.getRate().orElse(null),
                                driverState.getPrimaryPhone().orElse(null),
                                driverState.getPrimaryPhoneType().orElse(null),
                                driverState.getAltPhone().orElse(null),
                                driverState.getAltPhoneType().orElse(null),
                                driverState.getFax().orElse(null),
                                driverState.getEmail().orElse(null),
                                driverState.getStreetAddress().orElse(null),
                                driverState.getSecondStreetAddress().orElse(null),
                                driverState.getCity().orElse(null),
                                driverState.getState().orElse(null),
                                driverState.getZip().orElse(null),
                                driverState.getLicenseNumber().orElse(null),
                                driverState.getLicenseExpiration().orElse(null),
                                driverState.getLicenseDateIssued().orElse(null),
                                driverState.getLicenseStateIssue().orElse(null),
                                driverState.getLicenseClass().orElse(null),
                                driverState.getLicenseEndorsements().orElse(null)
                                )
                );
    }

    @Override
    public ServiceCall<DriverDTO, DriverDTO> changeDriverInformation(String id) {
        return request ->
                driverService.changeDriverInformation(id,
                Optional.ofNullable(request.firstName),
                Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                Optional.ofNullable(request.SSN), Optional.ofNullable(request.driverType),
                Optional.ofNullable(request.hireDate), Optional.ofNullable(request.terminationDate),
                Optional.ofNullable(request.status), Optional.ofNullable(request.paymentOptions),
                Optional.ofNullable(request.rate), Optional.ofNullable(request.primaryPhone),
                Optional.ofNullable(request.primaryPhoneType), Optional.ofNullable(request.altPhone),
                Optional.ofNullable(request.altPhoneType), Optional.ofNullable(request.fax),
                Optional.ofNullable(request.email), Optional.ofNullable(request.streetAddress),
                Optional.ofNullable(request.secondStreetAddress), Optional.ofNullable(request.city),
                Optional.ofNullable(request.state), Optional.ofNullable(request.zip),
                Optional.ofNullable(request.licenseNumber), Optional.ofNullable(request.licenseExpiration),
                Optional.ofNullable(request.licenseDateIssued), Optional.ofNullable(request.licenseStateIssue),
                Optional.ofNullable(request.licenseClass), Optional.ofNullable(request.licenseEndorsements))

                .thenApply(driverState ->
                        new DriverDTO(driverState.getId(),
                                driverState.getIsActive().orElse(false),
                                driverState.getFirstName().orElse(null),
                                driverState.getLastName().orElse(null),
                                driverState.getBirthDate().orElse(null),
                                driverState.getSSN().orElse(null),
                                driverState.getDriverType().orElse(null),
                                driverState.getHireDate().orElse(null),
                                driverState.getTerminationDate().orElse(null),
                                driverState.getStatus().orElse(null),
                                driverState.getPaymentOptions().orElse(null),
                                driverState.getRate().orElse(null),
                                driverState.getPrimaryPhone().orElse(null),
                                driverState.getPrimaryPhoneType().orElse(null),
                                driverState.getAltPhone().orElse(null),
                                driverState.getAltPhoneType().orElse(null),
                                driverState.getFax().orElse(null),
                                driverState.getEmail().orElse(null),
                                driverState.getStreetAddress().orElse(null),
                                driverState.getSecondStreetAddress().orElse(null),
                                driverState.getCity().orElse(null),
                                driverState.getState().orElse(null),
                                driverState.getZip().orElse(null),
                                driverState.getLicenseNumber().orElse(null),
                                driverState.getLicenseExpiration().orElse(null),
                                driverState.getLicenseDateIssued().orElse(null),
                                driverState.getLicenseStateIssue().orElse(null),
                                driverState.getLicenseClass().orElse(null),
                                driverState.getLicenseEndorsements().orElse(null)
                        )
                );
    }

    @Override
    public ServiceCall<DisableDriverDTO, Done> disableDriver(String id) {
        return request ->
                driverService.disableDriver(id);
    }

    @Override
    public ServiceCall<NotUsed, DriverDTO> getDriverInformation(String id) {
        return request ->
                driverService.getDriverInformation(id)

                .thenApply(driverState ->
                        new DriverDTO(driverState.getId(),
                                driverState.getIsActive().orElse(false),
                                driverState.getFirstName().orElse(null),
                                driverState.getLastName().orElse(null),
                                driverState.getBirthDate().orElse(null),
                                driverState.getSSN().orElse(null),
                                driverState.getDriverType().orElse(null),
                                driverState.getHireDate().orElse(null),
                                driverState.getTerminationDate().orElse(null),
                                driverState.getStatus().orElse(null),
                                driverState.getPaymentOptions().orElse(null),
                                driverState.getRate().orElse(null),
                                driverState.getPrimaryPhone().orElse(null),
                                driverState.getPrimaryPhoneType().orElse(null),
                                driverState.getAltPhone().orElse(null),
                                driverState.getAltPhoneType().orElse(null),
                                driverState.getFax().orElse(null),
                                driverState.getEmail().orElse(null),
                                driverState.getStreetAddress().orElse(null),
                                driverState.getSecondStreetAddress().orElse(null),
                                driverState.getCity().orElse(null),
                                driverState.getState().orElse(null),
                                driverState.getZip().orElse(null),
                                driverState.getLicenseNumber().orElse(null),
                                driverState.getLicenseExpiration().orElse(null),
                                driverState.getLicenseDateIssued().orElse(null),
                                driverState.getLicenseStateIssue().orElse(null),
                                driverState.getLicenseClass().orElse(null),
                                driverState.getLicenseEndorsements().orElse(null)
                        )
                );
    }
}
