package com.bridge18.expedition.services.lagom;

import akka.NotUsed;
import com.bridge18.expedition.api.LagomDriverService;
import com.bridge18.expedition.dto.v1.*;
import com.bridge18.expedition.entities.LicenseClass;
import com.bridge18.expedition.entities.PaymentOptions;
import com.bridge18.expedition.repository.DriverRepository;
import com.bridge18.expedition.services.objects.DriverService;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by Viktor on 01.03.2017.
 */
public class LagomDriverServiceImpl implements LagomDriverService {
    private DriverService driverService;

    private final DriverRepository driverRepository;

    @Inject
    public LagomDriverServiceImpl(DriverService driverService, DriverRepository driverRepository) {
        this.driverService = driverService;
        this.driverRepository = driverRepository;
    }

    @Override
    public ServiceCall<DriverDTO, DriverDTO> createNewDriver() {
        return request ->
                driverService.createDriver(Optional.ofNullable(request.id), Optional.ofNullable(request.position),
                        Optional.ofNullable(request.firstName), Optional.ofNullable(request.middleName),
                        Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                        Optional.ofNullable(request.SSN), Optional.ofNullable(PaymentOptions.values()[request.paymentOptions]),
                        Optional.ofNullable(request.rate), Optional.ofNullable(request.contactInfo),
                        Optional.ofNullable(request.address.id), Optional.ofNullable(request.address.name),
                        Optional.ofNullable(request.address.streetAddress1), Optional.ofNullable(request.address.streetAddress2),
                        Optional.ofNullable(request.address.city), Optional.ofNullable(request.address.phone),
                        Optional.ofNullable(request.address.state), Optional.ofNullable(request.address.zip),
                        Optional.ofNullable(request.address.fax), Optional.ofNullable(request.address.phoneExtension),
                        Optional.ofNullable(request.address.faxExtension), Optional.ofNullable(request.address.latitude),
                        Optional.ofNullable(request.address.longitude), Optional.ofNullable(request.license.number),
                        Optional.ofNullable(request.license.expiration), Optional.ofNullable(request.license.dateIssues),
                        Optional.ofNullable(request.license.stateIssued), Optional.ofNullable(LicenseClass.values()[request.license.licenseClass]),
                        Optional.ofNullable(request.license.endorsements), Optional.ofNullable(request.license.restrictions))

                .thenApply(driverState ->{
                    LicenseDTO licenseDTO = new LicenseDTO(driverState.getLicenseNumber().orElse(null),
                            driverState.getLicenseExpiration().orElse(null), driverState.getLicenseDateIssued().orElse(null),
                            driverState.getLicenseStateIssue().orElse(null), driverState.getLicenseClass().orElse(null).ordinal(),
                            driverState.getLicenseEndorsements().orElse(null), driverState.getLicenseRestrictions().orElse(null)
                    );
                    AddressDTO addressDTO = new AddressDTO(driverState.getAddressId().orElse(null),
                            driverState.getAddressName().orElse(null),driverState.getStreetAddress1().orElse(null),
                            driverState.getStreetAddress2().orElse(null),driverState.getCity().orElse(null),
                            driverState.getAddressPhone().orElse(null),driverState.getState().orElse(null),
                            driverState.getZip().orElse(null),driverState.getAddressFax().orElse(null),
                            driverState.getAddressPhoneExtension().orElse(null),driverState.getAddressFaxExtension().orElse(null),
                            driverState.getAddressLatitude().orElse(null),driverState.getAddressLongitude().orElse(null)
                            );
                    DriverDTO driverDTO = new DriverDTO(driverState.getId(), driverState.getContactId().orElse(null),
                            driverState.getFirstName().orElse(null), driverState.getMiddleName().orElse(null),
                            driverState.getLastName().orElse(null), driverState.getContactInfo().orElse(null),
                            driverState.getPosition().orElse(null), addressDTO, driverState.getBirthDate().orElse(null),
                            driverState.getSSN().orElse(null), driverState.getPaymentOptions().orElse(null).ordinal(),
                            driverState.getRate().orElse(null), licenseDTO
                            );
                    return driverDTO;
                }
                );
    }

    @Override
    public ServiceCall<DriverDTO, DriverDTO> changeDriverInformation(String id) {
        return request ->
                driverService.changeDriverInformation(id,
                        Optional.ofNullable(request.id), Optional.ofNullable(request.position),
                        Optional.ofNullable(request.firstName), Optional.ofNullable(request.middleName),
                        Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                        Optional.ofNullable(request.SSN), Optional.ofNullable(PaymentOptions.values()[request.paymentOptions]),
                        Optional.ofNullable(request.rate), Optional.ofNullable(request.contactInfo),
                        Optional.ofNullable(request.address.id), Optional.ofNullable(request.address.name),
                        Optional.ofNullable(request.address.streetAddress1), Optional.ofNullable(request.address.streetAddress2),
                        Optional.ofNullable(request.address.city), Optional.ofNullable(request.address.phone),
                        Optional.ofNullable(request.address.state), Optional.ofNullable(request.address.zip),
                        Optional.ofNullable(request.address.fax), Optional.ofNullable(request.address.phoneExtension),
                        Optional.ofNullable(request.address.faxExtension), Optional.ofNullable(request.address.latitude),
                        Optional.ofNullable(request.address.longitude), Optional.ofNullable(request.license.number),
                        Optional.ofNullable(request.license.expiration), Optional.ofNullable(request.license.dateIssues),
                        Optional.ofNullable(request.license.stateIssued), Optional.ofNullable(LicenseClass.values()[request.license.licenseClass]),
                        Optional.ofNullable(request.license.endorsements), Optional.ofNullable(request.license.restrictions))

                        .thenApply(driverState ->{
                            LicenseDTO licenseDTO = new LicenseDTO(driverState.getLicenseNumber().orElse(null),
                                    driverState.getLicenseExpiration().orElse(null), driverState.getLicenseDateIssued().orElse(null),
                                    driverState.getLicenseStateIssue().orElse(null), driverState.getLicenseClass().orElse(null).ordinal(),
                                    driverState.getLicenseEndorsements().orElse(null), driverState.getLicenseRestrictions().orElse(null)
                            );
                            AddressDTO addressDTO = new AddressDTO(driverState.getAddressId().orElse(null),
                                    driverState.getAddressName().orElse(null),driverState.getStreetAddress1().orElse(null),
                                    driverState.getStreetAddress2().orElse(null),driverState.getCity().orElse(null),
                                    driverState.getAddressPhone().orElse(null),driverState.getState().orElse(null),
                                    driverState.getZip().orElse(null),driverState.getAddressFax().orElse(null),
                                    driverState.getAddressPhoneExtension().orElse(null),driverState.getAddressFaxExtension().orElse(null),
                                    driverState.getAddressLatitude().orElse(null),driverState.getAddressLongitude().orElse(null)
                            );
                            DriverDTO driverDTO = new DriverDTO(driverState.getId(), driverState.getContactId().orElse(null),
                                    driverState.getFirstName().orElse(null), driverState.getMiddleName().orElse(null),
                                    driverState.getLastName().orElse(null), driverState.getContactInfo().orElse(null),
                                    driverState.getPosition().orElse(null), addressDTO, driverState.getBirthDate().orElse(null),
                                    driverState.getSSN().orElse(null), driverState.getPaymentOptions().orElse(null).ordinal(),
                                    driverState.getRate().orElse(null), licenseDTO
                            );
                            return driverDTO;
                        }
                );
    }

    @Override
    public ServiceCall<NotUsed, PaginatedSequence<DriverSummary>> getDriverSummaries(Optional<Integer> pageNo, Optional<Integer> pageSize) {
        return request -> driverRepository.getDrivers(pageNo.orElse(0), pageSize.orElse(10));
    }

    @Override
    public ServiceCall<NotUsed, DriverDTO> getDriverInformation(String id) {
        return request ->
                driverService.getDriverInformation(id)

                .thenApply(driverState ->{
                            LicenseDTO licenseDTO = new LicenseDTO(driverState.getLicenseNumber().orElse(null),
                                    driverState.getLicenseExpiration().orElse(null), driverState.getLicenseDateIssued().orElse(null),
                                    driverState.getLicenseStateIssue().orElse(null), driverState.getLicenseClass().orElse(null).ordinal(),
                                    driverState.getLicenseEndorsements().orElse(null), driverState.getLicenseRestrictions().orElse(null)
                            );
                            AddressDTO addressDTO = new AddressDTO(driverState.getAddressId().orElse(null),
                                    driverState.getAddressName().orElse(null),driverState.getStreetAddress1().orElse(null),
                                    driverState.getStreetAddress2().orElse(null),driverState.getCity().orElse(null),
                                    driverState.getAddressPhone().orElse(null),driverState.getState().orElse(null),
                                    driverState.getZip().orElse(null),driverState.getAddressFax().orElse(null),
                                    driverState.getAddressPhoneExtension().orElse(null),driverState.getAddressFaxExtension().orElse(null),
                                    driverState.getAddressLatitude().orElse(null),driverState.getAddressLongitude().orElse(null)
                            );
                            DriverDTO driverDTO = new DriverDTO(driverState.getId(), driverState.getContactId().orElse(null),
                                    driverState.getFirstName().orElse(null), driverState.getMiddleName().orElse(null),
                                    driverState.getLastName().orElse(null), driverState.getContactInfo().orElse(null),
                                    driverState.getPosition().orElse(null), addressDTO, driverState.getBirthDate().orElse(null),
                                    driverState.getSSN().orElse(null), driverState.getPaymentOptions().orElse(null).ordinal(),
                                    driverState.getRate().orElse(null), licenseDTO
                            );
                            return driverDTO;
                        }
                );
    }
}
