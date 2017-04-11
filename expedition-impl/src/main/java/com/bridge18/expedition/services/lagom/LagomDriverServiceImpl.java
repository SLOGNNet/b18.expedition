package com.bridge18.expedition.services.lagom;

import akka.NotUsed;
import com.bridge18.expedition.api.LagomDriverService;
import com.bridge18.expedition.dto.v1.*;
import com.bridge18.expedition.entities.driver.Address;
import com.bridge18.expedition.entities.driver.ContactInfo;
import com.bridge18.expedition.entities.driver.DriverState;
import com.bridge18.expedition.entities.driver.License;
import com.bridge18.expedition.repository.DriverRepository;
import com.bridge18.expedition.services.objects.DriverService;
import com.google.common.collect.Lists;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


public class LagomDriverServiceImpl implements LagomDriverService {
    private DriverService driverService;

    private final DriverRepository driverRepository;

    static final int PAGE_SIZE = 20;

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
                            Lists.transform(request.contactInfo, contactInfoDTO -> ContactInfo.builder()
                                    .label(Optional.ofNullable(contactInfoDTO.label))
                                    .value(Optional.ofNullable(contactInfoDTO.value))
                                    .type(Optional.ofNullable(contactInfoDTO.type))
                                    .build()
                            )
                    )  : null;
            return driverService.createDriver(Optional.ofNullable(request.position),
                    Optional.ofNullable(request.firstName), Optional.ofNullable(request.middleName),
                    Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                    Optional.ofNullable(request.ssn), Optional.ofNullable(request.paymentOption),
                    Optional.ofNullable(request.rate), Optional.ofNullable(request.type),
                    Optional.ofNullable(inContactInfo),
                    Optional.ofNullable(convertAddressDTOToAddress(request.address)),
                    Optional.ofNullable(convertLicenseDTOToLicense(request.license)))

                    .thenApply(this::convertDriverStateToDriverDTO);
        };

    }

    @Override
    public ServiceCall<DriverDTO, DriverDTO> updateDriverInformation(String id) {
        return request ->{
            PVector<ContactInfo> inContactInfo = Optional.ofNullable(request.contactInfo).isPresent() ?
                    TreePVector.from(
                            Lists.transform(request.contactInfo, contactInfoDTO -> ContactInfo.builder()
                                    .label(Optional.ofNullable(contactInfoDTO.label))
                                    .value(Optional.ofNullable(contactInfoDTO.value))
                                    .type(Optional.ofNullable(contactInfoDTO.type))
                                    .build()
                            )
                    )  : null;
            return driverService.updateDriver(id, Optional.ofNullable(request.position),
                    Optional.ofNullable(request.firstName), Optional.ofNullable(request.middleName),
                    Optional.ofNullable(request.lastName), Optional.ofNullable(request.birthDate),
                    Optional.ofNullable(request.ssn), Optional.ofNullable(request.paymentOption),
                    Optional.ofNullable(request.rate), Optional.ofNullable(request.type),
                    Optional.ofNullable(inContactInfo),
                    Optional.ofNullable(convertAddressDTOToAddress(request.address)),
                    Optional.ofNullable(convertLicenseDTOToLicense(request.license)))
                    .thenApply(this::convertDriverStateToDriverDTO);
        };
    }

    @Override
    public ServiceCall<NotUsed, PaginatedSequence<DriverSummary>> getDriverSummaries(Optional<String> pagingState, Optional<Integer> pageSize) {
        return request -> driverRepository.getDrivers(pagingState.orElse(null), pageSize.orElse(PAGE_SIZE));
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

        Address address = driverState.getAddress().get();
        AddressDTO addressDTO = driverState.getAddress().isPresent() ?
            new AddressDTO(address.getAddressId().orElse(null),
                    address.getAddressName().orElse(null),address.getStreetAddress1().orElse(null),
                    address.getStreetAddress2().orElse(null),address.getCity().orElse(null),
                    address.getAddressPhone().orElse(null),address.getState().orElse(null),
                    address.getZip().orElse(null),address.getAddressFax().orElse(null),
                    address.getAddressPhoneExtension().orElse(null),address.getAddressFaxExtension().orElse(null),
                    address.getAddressLatitude().orElse(null),address.getAddressLongitude().orElse(null)
        ) : null;

        License license = driverState.getLicense().get();
        LicenseDTO licenseDTO = driverState.getLicense().isPresent() ?
                new LicenseDTO(
                        license.getLicenseNumber().orElse(null),
                        license.getLicenseExpiration().orElse(null), license.getLicenseDateIssued().orElse(null),
                        license.getLicenseStateIssued().orElse(null), license.getLicenseClass().orElse(null),
                        license.getLicenseEndorsements().orElse(null), license.getLicenseRestrictions().orElse(null)
                ) : null;

        DriverDTO driverDTO = new DriverDTO(driverState.getId(),
                driverState.getFirstName().orElse(null), driverState.getMiddleName().orElse(null),
                driverState.getLastName().orElse(null), contactInfoDTOList,
                driverState.getPosition().orElse(null), addressDTO, driverState.getBirthDate().orElse(null),
                driverState.getSsn().orElse(null), driverState.getPaymentOption().orElse(null),
                driverState.getRate().orElse(null), driverState.getDriverType().orElse(null), licenseDTO
        );
        return driverDTO;
    }

    private Address convertAddressDTOToAddress(AddressDTO addressDTO){
        addressDTO = Optional.ofNullable(addressDTO).orElse(new AddressDTO());
        return Address.builder()
                .addressId(Optional.ofNullable(addressDTO.id))
                .addressName(Optional.ofNullable(addressDTO.name))
                .streetAddress1(Optional.ofNullable(addressDTO.streetAddress1))
                .streetAddress2(Optional.ofNullable(addressDTO.streetAddress2))
                .city(Optional.ofNullable(addressDTO.city))
                .addressPhone(Optional.ofNullable(addressDTO.phone))
                .state(Optional.ofNullable(addressDTO.state))
                .zip(Optional.ofNullable(addressDTO.zip))
                .addressFax(Optional.ofNullable(addressDTO.fax))
                .addressPhoneExtension(Optional.ofNullable(addressDTO.phoneExtension))
                .addressFaxExtension(Optional.ofNullable(addressDTO.faxExtension))
                .addressLatitude(Optional.ofNullable(addressDTO.latitude))
                .addressLongitude(Optional.ofNullable(addressDTO.longitude))
                .build();
    }

    private License convertLicenseDTOToLicense(LicenseDTO licenseDTO){
        licenseDTO = Optional.ofNullable(licenseDTO).orElse(new LicenseDTO());
        return License.builder()
                .licenseNumber(Optional.ofNullable(licenseDTO.number))
                .licenseExpiration(Optional.ofNullable(licenseDTO.expiration))
                .licenseDateIssued(Optional.ofNullable(licenseDTO.dateIssued))
                .licenseStateIssued(Optional.ofNullable(licenseDTO.stateIssued))
                .licenseClass(Optional.ofNullable(licenseDTO.licenseClass))
                .licenseEndorsements(Optional.ofNullable(licenseDTO.endorsements))
                .licenseRestrictions(Optional.ofNullable(licenseDTO.restrictions))
                .build();
    }
}
