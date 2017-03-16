package com.bridge18.expedition.services.lagom;

import akka.NotUsed;
import com.bridge18.expedition.api.LagomDriverService;
import com.bridge18.expedition.dto.v1.*;
import com.bridge18.expedition.entities.driver.ContactInfo;
import com.bridge18.expedition.entities.driver.DriverState;
import com.bridge18.expedition.repository.DriverRepository;
import com.bridge18.expedition.services.objects.DriverService;
import com.google.common.collect.Lists;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.function.Function;

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
            PVector<ContactInfo> inContactInfo = Optional.ofNullable(request.contactInfo).isPresent() ?
                    TreePVector.from(
                            Lists.transform(request.contactInfo, contactInfoDTO -> new ContactInfo(
                                    Optional.ofNullable(contactInfoDTO.label),
                                    Optional.ofNullable(contactInfoDTO.value),
                                    Optional.ofNullable(contactInfoDTO.type)
                            ))
                    )  : null;
            AddressDTO inAddressDTO = Optional.ofNullable(request.address).orElse(new AddressDTO());
            LicenseDTO inLicenseDTO = Optional.ofNullable(request.license).orElse(new LicenseDTO());
            return driverService.createDriver(Optional.ofNullable(request.id), Optional.ofNullable(request.position),
                    Optional.ofNullable(request.firstName), Optional.ofNullable(request.middleName),
                    Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                    Optional.ofNullable(request.SSN), Optional.ofNullable(request.paymentOptions),
                    Optional.ofNullable(request.rate), Optional.ofNullable(inContactInfo),
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

                    .thenApply(this::convertDriverStateToDriverDTO);
        };

    }

    @Override
    public ServiceCall<DriverDTO, DriverDTO> updateDriverInformation(String id) {
        return request ->{
            PVector<ContactInfo> inContactInfo = Optional.ofNullable(request.contactInfo).isPresent() ?
                    TreePVector.from(
                            Lists.transform(request.contactInfo, contactInfoDTO -> new ContactInfo(
                                    Optional.ofNullable(contactInfoDTO.label),
                                    Optional.ofNullable(contactInfoDTO.value),
                                    Optional.ofNullable(contactInfoDTO.type)
                            ))
                    )  : null;
            AddressDTO inAddressDTO = Optional.ofNullable(request.address).orElse(new AddressDTO());
            LicenseDTO inLicenseDTO = Optional.ofNullable(request.license).orElse(new LicenseDTO());
            return driverService.changeDriverInformation(id,
                    Optional.ofNullable(request.id), Optional.ofNullable(request.position),
                    Optional.ofNullable(request.firstName), Optional.ofNullable(request.middleName),
                    Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                    Optional.ofNullable(request.SSN), Optional.ofNullable(request.paymentOptions),
                    Optional.ofNullable(request.rate), Optional.ofNullable(inContactInfo),
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

                    .thenApply(this::convertDriverStateToDriverDTO);
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
                        .thenApply(this::convertDriverStateToDriverDTO);
    }

    private DriverDTO convertDriverStateToDriverDTO(DriverState driverState) {
        List<ContactInfoDTO> contactInfoDTOList = driverState.getContactInfo().isPresent() ?
                Lists.transform(driverState.getContactInfo().get(), contactInfo ->
                        new ContactInfoDTO(contactInfo.getLabel().orElse(null),
                                contactInfo.getValue().orElse(null),
                                contactInfo.getType().orElse(null)
                        )
                ) : null;
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
                driverState.getLastName().orElse(null), contactInfoDTOList,
                driverState.getPosition().orElse(null), outAddressDTO, driverState.getBirthDate().orElse(null),
                driverState.getSSN().orElse(null), driverState.getPaymentOptions().orElse(null),
                driverState.getRate().orElse(null), outLicenseDTO
        );
        return driverDTO;
    }
}
