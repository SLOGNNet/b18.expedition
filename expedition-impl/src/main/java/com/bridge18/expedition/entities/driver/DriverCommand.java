package com.bridge18.expedition.entities.driver;

import com.bridge18.expedition.entities.LicenseClass;
import com.bridge18.expedition.entities.PaymentOptions;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.Optional;


public interface DriverCommand extends Jsonable {
    @Immutable
    @JsonDeserialize
    final class CreateDriver implements DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState> {
        public Optional<Integer> contactId;
        public Optional<String> position;
        public Optional<String> firstName;
        public Optional<String> middleName;
        public Optional<String> lastName;
        public Optional<Date> birthDate;
        public Optional<String> SSN;


        public Optional<PaymentOptions> paymentOptions;
        public Optional<Double> rate;

        public Optional<String> contactInfo;

        public Optional<Integer> addressId;
        public Optional<String> addressName;
        public Optional<String> streetAddress1;
        public Optional<String> streetAddress2;
        public Optional<String> city;
        public Optional<String> addressPhone;
        public Optional<String> state;
        public Optional<String> zip;
        public Optional<String> addressFax;
        public Optional<String> addressPhoneExtension;
        public Optional<String> addressFaxExtension;
        public Optional<Double> addressLatitude;
        public Optional<Double> addressLongitude;

        public Optional<Integer> licenseNumber;
        public Optional<Date> licenseExpiration;
        public Optional<Date> licenseDateIssued;
        public Optional<String> licenseStateIssue;
        public Optional<LicenseClass> licenseClass;
        public Optional<String> licenseEndorsements;
        public Optional<String> licenseRestrictions;

        public CreateDriver() {
        }

        public CreateDriver(Optional<Integer> contactId, Optional<String> position, Optional<String> firstName, Optional<String> middleName, Optional<String> lastName, Optional<Date> birthDate, Optional<String> SSN, Optional<PaymentOptions> paymentOptions, Optional<Double> rate, Optional<String> contactInfo, Optional<Integer> addressId, Optional<String> addressName, Optional<String> streetAddress1, Optional<String> streetAddress2, Optional<String> city, Optional<String> addressPhone, Optional<String> state, Optional<String> zip, Optional<String> addressFax, Optional<String> addressPhoneExtension, Optional<String> addressFaxExtension, Optional<Double> addressLatitude, Optional<Double> addressLongitude, Optional<Integer> licenseNumber, Optional<Date> licenseExpiration, Optional<Date> licenseDateIssued, Optional<String> licenseStateIssue, Optional<LicenseClass> licenseClass, Optional<String> licenseEndorsements, Optional<String> licenseRestrictions) {
            this.contactId = contactId;
            this.position = position;
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.SSN = SSN;
            this.paymentOptions = paymentOptions;
            this.rate = rate;
            this.contactInfo = contactInfo;
            this.addressId = addressId;
            this.addressName = addressName;
            this.streetAddress1 = streetAddress1;
            this.streetAddress2 = streetAddress2;
            this.city = city;
            this.addressPhone = addressPhone;
            this.state = state;
            this.zip = zip;
            this.addressFax = addressFax;
            this.addressPhoneExtension = addressPhoneExtension;
            this.addressFaxExtension = addressFaxExtension;
            this.addressLatitude = addressLatitude;
            this.addressLongitude = addressLongitude;
            this.licenseNumber = licenseNumber;
            this.licenseExpiration = licenseExpiration;
            this.licenseDateIssued = licenseDateIssued;
            this.licenseStateIssue = licenseStateIssue;
            this.licenseClass = licenseClass;
            this.licenseEndorsements = licenseEndorsements;
            this.licenseRestrictions = licenseRestrictions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            CreateDriver that = (CreateDriver) o;

            return new EqualsBuilder()
                    .append(contactId, that.contactId)
                    .append(position, that.position)
                    .append(firstName, that.firstName)
                    .append(middleName, that.middleName)
                    .append(lastName, that.lastName)
                    .append(birthDate, that.birthDate)
                    .append(SSN, that.SSN)
                    .append(paymentOptions, that.paymentOptions)
                    .append(rate, that.rate)
                    .append(contactInfo, that.contactInfo)
                    .append(addressId, that.addressId)
                    .append(addressName, that.addressName)
                    .append(streetAddress1, that.streetAddress1)
                    .append(streetAddress2, that.streetAddress2)
                    .append(city, that.city)
                    .append(addressPhone, that.addressPhone)
                    .append(state, that.state)
                    .append(zip, that.zip)
                    .append(addressFax, that.addressFax)
                    .append(addressPhoneExtension, that.addressPhoneExtension)
                    .append(addressFaxExtension, that.addressFaxExtension)
                    .append(addressLatitude, that.addressLatitude)
                    .append(addressLongitude, that.addressLongitude)
                    .append(licenseNumber, that.licenseNumber)
                    .append(licenseExpiration, that.licenseExpiration)
                    .append(licenseDateIssued, that.licenseDateIssued)
                    .append(licenseStateIssue, that.licenseStateIssue)
                    .append(licenseClass, that.licenseClass)
                    .append(licenseEndorsements, that.licenseEndorsements)
                    .append(licenseRestrictions, that.licenseRestrictions)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(contactId)
                    .append(position)
                    .append(firstName)
                    .append(middleName)
                    .append(lastName)
                    .append(birthDate)
                    .append(SSN)
                    .append(paymentOptions)
                    .append(rate)
                    .append(contactInfo)
                    .append(addressId)
                    .append(addressName)
                    .append(streetAddress1)
                    .append(streetAddress2)
                    .append(city)
                    .append(addressPhone)
                    .append(state)
                    .append(zip)
                    .append(addressFax)
                    .append(addressPhoneExtension)
                    .append(addressFaxExtension)
                    .append(addressLatitude)
                    .append(addressLongitude)
                    .append(licenseNumber)
                    .append(licenseExpiration)
                    .append(licenseDateIssued)
                    .append(licenseStateIssue)
                    .append(licenseClass)
                    .append(licenseEndorsements)
                    .append(licenseRestrictions)
                    .toHashCode();
        }
    }

    @Immutable
    @JsonDeserialize
    final class UpdateDriver implements DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState>{
        public Optional<Integer> contactId;
        public Optional<String> position;
        public Optional<String> firstName;
        public Optional<String> middleName;
        public Optional<String> lastName;
        public Optional<Date> birthDate;
        public Optional<String> SSN;


        public Optional<PaymentOptions> paymentOptions;
        public Optional<Double> rate;

        public Optional<String> contactInfo;

        public Optional<Integer> addressId;
        public Optional<String> addressName;
        public Optional<String> streetAddress1;
        public Optional<String> streetAddress2;
        public Optional<String> city;
        public Optional<String> addressPhone;
        public Optional<String> state;
        public Optional<String> zip;
        public Optional<String> addressFax;
        public Optional<String> addressPhoneExtension;
        public Optional<String> addressFaxExtension;
        public Optional<Double> addressLatitude;
        public Optional<Double> addressLongitude;

        public Optional<Integer> licenseNumber;
        public Optional<Date> licenseExpiration;
        public Optional<Date> licenseDateIssued;
        public Optional<String> licenseStateIssue;
        public Optional<LicenseClass> licenseClass;
        public Optional<String> licenseEndorsements;
        public Optional<String> licenseRestrictions;

        public UpdateDriver() {
        }

        public UpdateDriver(Optional<Integer> contactId, Optional<String> position, Optional<String> firstName, Optional<String> middleName, Optional<String> lastName, Optional<Date> birthDate, Optional<String> SSN, Optional<PaymentOptions> paymentOptions, Optional<Double> rate, Optional<String> contactInfo, Optional<Integer> addressId, Optional<String> addressName, Optional<String> streetAddress1, Optional<String> streetAddress2, Optional<String> city, Optional<String> addressPhone, Optional<String> state, Optional<String> zip, Optional<String> addressFax, Optional<String> addressPhoneExtension, Optional<String> addressFaxExtension, Optional<Double> addressLatitude, Optional<Double> addressLongitude, Optional<Integer> licenseNumber, Optional<Date> licenseExpiration, Optional<Date> licenseDateIssued, Optional<String> licenseStateIssue, Optional<LicenseClass> licenseClass, Optional<String> licenseEndorsements, Optional<String> licenseRestrictions) {
            this.contactId = contactId;
            this.position = position;
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.SSN = SSN;
            this.paymentOptions = paymentOptions;
            this.rate = rate;
            this.contactInfo = contactInfo;
            this.addressId = addressId;
            this.addressName = addressName;
            this.streetAddress1 = streetAddress1;
            this.streetAddress2 = streetAddress2;
            this.city = city;
            this.addressPhone = addressPhone;
            this.state = state;
            this.zip = zip;
            this.addressFax = addressFax;
            this.addressPhoneExtension = addressPhoneExtension;
            this.addressFaxExtension = addressFaxExtension;
            this.addressLatitude = addressLatitude;
            this.addressLongitude = addressLongitude;
            this.licenseNumber = licenseNumber;
            this.licenseExpiration = licenseExpiration;
            this.licenseDateIssued = licenseDateIssued;
            this.licenseStateIssue = licenseStateIssue;
            this.licenseClass = licenseClass;
            this.licenseEndorsements = licenseEndorsements;
            this.licenseRestrictions = licenseRestrictions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            UpdateDriver that = (UpdateDriver) o;

            return new EqualsBuilder()
                    .append(contactId, that.contactId)
                    .append(position, that.position)
                    .append(firstName, that.firstName)
                    .append(middleName, that.middleName)
                    .append(lastName, that.lastName)
                    .append(birthDate, that.birthDate)
                    .append(SSN, that.SSN)
                    .append(paymentOptions, that.paymentOptions)
                    .append(rate, that.rate)
                    .append(contactInfo, that.contactInfo)
                    .append(addressId, that.addressId)
                    .append(addressName, that.addressName)
                    .append(streetAddress1, that.streetAddress1)
                    .append(streetAddress2, that.streetAddress2)
                    .append(city, that.city)
                    .append(addressPhone, that.addressPhone)
                    .append(state, that.state)
                    .append(zip, that.zip)
                    .append(addressFax, that.addressFax)
                    .append(addressPhoneExtension, that.addressPhoneExtension)
                    .append(addressFaxExtension, that.addressFaxExtension)
                    .append(addressLatitude, that.addressLatitude)
                    .append(addressLongitude, that.addressLongitude)
                    .append(licenseNumber, that.licenseNumber)
                    .append(licenseExpiration, that.licenseExpiration)
                    .append(licenseDateIssued, that.licenseDateIssued)
                    .append(licenseStateIssue, that.licenseStateIssue)
                    .append(licenseClass, that.licenseClass)
                    .append(licenseEndorsements, that.licenseEndorsements)
                    .append(licenseRestrictions, that.licenseRestrictions)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(contactId)
                    .append(position)
                    .append(firstName)
                    .append(middleName)
                    .append(lastName)
                    .append(birthDate)
                    .append(SSN)
                    .append(paymentOptions)
                    .append(rate)
                    .append(contactInfo)
                    .append(addressId)
                    .append(addressName)
                    .append(streetAddress1)
                    .append(streetAddress2)
                    .append(city)
                    .append(addressPhone)
                    .append(state)
                    .append(zip)
                    .append(addressFax)
                    .append(addressPhoneExtension)
                    .append(addressFaxExtension)
                    .append(addressLatitude)
                    .append(addressLongitude)
                    .append(licenseNumber)
                    .append(licenseExpiration)
                    .append(licenseDateIssued)
                    .append(licenseStateIssue)
                    .append(licenseClass)
                    .append(licenseEndorsements)
                    .append(licenseRestrictions)
                    .toHashCode();
        }
    }

    @Immutable
    @JsonDeserialize
    final class GetDriverInformation implements DriverCommand, CompressedJsonable, PersistentEntity.ReplyType<DriverState> {

        public GetDriverInformation() {
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof GetDriverInformation) {
                return true;
            }
            return super.equals(obj);
        }
    }
}
