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
        return request ->{
            AddressDTO inAddressDTO = Optional.ofNullable(request.address).orElse(new AddressDTO());
            LicenseDTO inLicenseDTO = Optional.ofNullable(request.license).orElse(new LicenseDTO());
            return driverService.createDriver(Optional.ofNullable(request.id), Optional.ofNullable(request.position),
                    Optional.ofNullable(request.firstName), Optional.ofNullable(request.middleName),
                    Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                    Optional.ofNullable(request.SSN), Optional.ofNullable(request.paymentOptions),
                    Optional.ofNullable(request.rate), Optional.ofNullable(request.contactInfo),
                    Optional.ofNullable(inAddressDTO.id), Optional.ofNullable(inAddressDTO.name),
                    Optional.ofNullable(inAddressDTO.streetAddress1), Optional.ofNullable(inAddressDTO.streetAddress2),
                    Optional.ofNullable(inAddressDTO.city), Optional.ofNullable(inAddressDTO.phone),
                    Optional.ofNullable(inAddressDTO.state), Optional.ofNullable(inAddressDTO.zip),
                    Optional.ofNullable(inAddressDTO.fax), Optional.ofNullable(inAddressDTO.phoneExtension),
                    Optional.ofNullable(inAddressDTO.faxExtension), Optional.ofNullable(inAddressDTO.latitude),
                    Optional.ofNullable(inAddressDTO.longitude), Optional.ofNullable(inLicenseDTO.number),
                    Optional.ofNullable(inLicenseDTO.expiration), Optional.ofNullable(inLicenseDTO.dateIssues),
                    Optional.ofNullable(inLicenseDTO.stateIssued), Optional.ofNullable(inLicenseDTO.licenseClass),
                    Optional.ofNullable(inLicenseDTO.endorsements), Optional.ofNullable(inLicenseDTO.restrictions))

                    .thenApply(driverState ->{
                        LicenseDTO outLicenseDTO = new LicenseDTO(driverState.getLicenseNumber().orElse(null),
                                driverState.getLicenseExpiration().orElse(null), driverState.getLicenseDateIssued().orElse(null),
                                driverState.getLicenseStateIssue().orElse(null), driverState.getLicenseClass().orElse(null),
                                driverState.getLicenseEndorsements().orElse(null), driverState.getLicenseRestrictions().orElse(null)
                        );
                        AddressDTO outAddressDTO = new AddressDTO(driverState.getAddressId().orElse(null),
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
                                driverState.getPosition().orElse(null), outAddressDTO, driverState.getBirthDate().orElse(null),
                                driverState.getSSN().orElse(null), driverState.getPaymentOptions().orElse(null),
                                driverState.getRate().orElse(null), outLicenseDTO
                        );
                        return driverDTO;
                    }
                    );
        };

    }

    @Override
    public ServiceCall<DriverDTO, DriverDTO> updateDriverInformation(String id) {
        return request ->{
            AddressDTO inAddressDTO = Optional.ofNullable(request.address).orElse(new AddressDTO());
            LicenseDTO inLicenseDTO = Optional.ofNullable(request.license).orElse(new LicenseDTO());
            return driverService.changeDriverInformation(id,
                    Optional.ofNullable(request.id), Optional.ofNullable(request.position),
                    Optional.ofNullable(request.firstName), Optional.ofNullable(request.middleName),
                    Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                    Optional.ofNullable(request.SSN), Optional.ofNullable(request.paymentOptions),
                    Optional.ofNullable(request.rate), Optional.ofNullable(request.contactInfo),
                    Optional.ofNullable(inAddressDTO.id), Optional.ofNullable(inAddressDTO.name),
                    Optional.ofNullable(inAddressDTO.streetAddress1), Optional.ofNullable(inAddressDTO.streetAddress2),
                    Optional.ofNullable(inAddressDTO.city), Optional.ofNullable(inAddressDTO.phone),
                    Optional.ofNullable(inAddressDTO.state), Optional.ofNullable(inAddressDTO.zip),
                    Optional.ofNullable(inAddressDTO.fax), Optional.ofNullable(inAddressDTO.phoneExtension),
                    Optional.ofNullable(inAddressDTO.faxExtension), Optional.ofNullable(inAddressDTO.latitude),
                    Optional.ofNullable(inAddressDTO.longitude), Optional.ofNullable(inLicenseDTO.number),
                    Optional.ofNullable(inLicenseDTO.expiration), Optional.ofNullable(inLicenseDTO.dateIssues),
                    Optional.ofNullable(inLicenseDTO.stateIssued), Optional.ofNullable(inLicenseDTO.licenseClass),
                    Optional.ofNullable(inLicenseDTO.endorsements), Optional.ofNullable(inLicenseDTO.restrictions))

                    .thenApply(driverState ->{
                                LicenseDTO outLicenseDTO = new LicenseDTO(driverState.getLicenseNumber().orElse(null),
                                        driverState.getLicenseExpiration().orElse(null), driverState.getLicenseDateIssued().orElse(null),
                                        driverState.getLicenseStateIssue().orElse(null), driverState.getLicenseClass().orElse(null),
                                        driverState.getLicenseEndorsements().orElse(null), driverState.getLicenseRestrictions().orElse(null)
                                );
                                AddressDTO outAddressDTO = new AddressDTO(driverState.getAddressId().orElse(null),
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
                                        driverState.getPosition().orElse(null), outAddressDTO, driverState.getBirthDate().orElse(null),
                                        driverState.getSSN().orElse(null), driverState.getPaymentOptions().orElse(null),
                                        driverState.getRate().orElse(null), outLicenseDTO
                                );
                                return driverDTO;
                            }
                    );
        };
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
                                    LicenseDTO outLicenseDTO = new LicenseDTO(driverState.getLicenseNumber().orElse(null),
                                            driverState.getLicenseExpiration().orElse(null), driverState.getLicenseDateIssued().orElse(null),
                                            driverState.getLicenseStateIssue().orElse(null), driverState.getLicenseClass().orElse(null),
                                            driverState.getLicenseEndorsements().orElse(null), driverState.getLicenseRestrictions().orElse(null)
                                    );
                                    AddressDTO outAddressDTO = new AddressDTO(driverState.getAddressId().orElse(null),
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
                                            driverState.getPosition().orElse(null), outAddressDTO, driverState.getBirthDate().orElse(null),
                                            driverState.getSSN().orElse(null), driverState.getPaymentOptions().orElse(null),
                                            driverState.getRate().orElse(null), outLicenseDTO
                                    );
                                    return driverDTO;
                                }
                        );
    }
}
