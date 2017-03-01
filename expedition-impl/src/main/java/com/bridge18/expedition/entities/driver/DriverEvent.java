package com.bridge18.expedition.entities.driver;

import com.lightbend.lagom.serialization.Jsonable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.Optional;


public interface DriverEvent extends Jsonable {
    @Immutable
    final class DriverCreated implements DriverEvent{
        public Optional<String> firstName;
        public Optional<String> lastName;
        public Optional<Date> birthDate;
        public Optional<String> SSN;

        public Optional<DriverType> driverType;
        public Optional<Date> hireDate;
        public Optional<Date> terminationDate;
        public Optional<EmploymentStatus> status;

        public Optional<PaymentOptions> paymentOptions;
        public Optional<Double> rate;

        public Optional<String> primaryPhone;
        public Optional<PhoneType> primaryPhoneType;
        public Optional<String> altPhone;
        public Optional<PhoneType> altPhoneType;
        public Optional<String> fax;
        public Optional<String> email;

        public Optional<String> streetAddress;
        public Optional<String> secondStreetAddress;
        public Optional<String> city;
        public Optional<String> state;
        public Optional<String> zip;

        public Optional<String> licenseNumber;
        public Optional<Date> licenseExpiration;
        public Optional<Date> licenseDateIssued;
        public Optional<LicenseStateIssue> licenseStateIssue;
        public Optional<LicenseClass> licenseClass;
        public Optional<LicenseEndorsements> licenseEndorsements;

        public DriverCreated() {
        }

        public DriverCreated(Optional<String> firstName, Optional<String> lastName, Optional<Date> birthDate, Optional<String> SSN, Optional<DriverType> driverType, Optional<Date> hireDate, Optional<Date> terminationDate, Optional<EmploymentStatus> status, Optional<PaymentOptions> paymentOptions, Optional<Double> rate, Optional<String> primaryPhone, Optional<PhoneType> primaryPhoneType, Optional<String> altPhone, Optional<PhoneType> altPhoneType, Optional<String> fax, Optional<String> email, Optional<String> streetAddress, Optional<String> secondStreetAddress, Optional<String> city, Optional<String> state, Optional<String> zip, Optional<String> licenseNumber, Optional<Date> licenseExpiration, Optional<Date> licenseDateIssued, Optional<LicenseStateIssue> licenseStateIssue, Optional<LicenseClass> licenseClass, Optional<LicenseEndorsements> licenseEndorsements) {

            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.SSN = SSN;
            this.driverType = driverType;
            this.hireDate = hireDate;
            this.terminationDate = terminationDate;
            this.status = status;
            this.paymentOptions = paymentOptions;
            this.rate = rate;
            this.primaryPhone = primaryPhone;
            this.primaryPhoneType = primaryPhoneType;
            this.altPhone = altPhone;
            this.altPhoneType = altPhoneType;
            this.fax = fax;
            this.email = email;
            this.streetAddress = streetAddress;
            this.secondStreetAddress = secondStreetAddress;
            this.city = city;
            this.state = state;
            this.zip = zip;
            this.licenseNumber = licenseNumber;
            this.licenseExpiration = licenseExpiration;
            this.licenseDateIssued = licenseDateIssued;
            this.licenseStateIssue = licenseStateIssue;
            this.licenseClass = licenseClass;
            this.licenseEndorsements = licenseEndorsements;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            DriverCreated that = (DriverCreated) o;

            return new EqualsBuilder()
                    .append(firstName, that.firstName)
                    .append(lastName, that.lastName)
                    .append(birthDate, that.birthDate)
                    .append(SSN, that.SSN)
                    .append(driverType, that.driverType)
                    .append(hireDate, that.hireDate)
                    .append(terminationDate, that.terminationDate)
                    .append(status, that.status)
                    .append(paymentOptions, that.paymentOptions)
                    .append(rate, that.rate)
                    .append(primaryPhone, that.primaryPhone)
                    .append(primaryPhoneType, that.primaryPhoneType)
                    .append(altPhone, that.altPhone)
                    .append(altPhoneType, that.altPhoneType)
                    .append(fax, that.fax)
                    .append(email, that.email)
                    .append(streetAddress, that.streetAddress)
                    .append(secondStreetAddress, that.secondStreetAddress)
                    .append(city, that.city)
                    .append(state, that.state)
                    .append(zip, that.zip)
                    .append(licenseNumber, that.licenseNumber)
                    .append(licenseExpiration, that.licenseExpiration)
                    .append(licenseDateIssued, that.licenseDateIssued)
                    .append(licenseStateIssue, that.licenseStateIssue)
                    .append(licenseClass, that.licenseClass)
                    .append(licenseEndorsements, that.licenseEndorsements)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(firstName)
                    .append(lastName)
                    .append(birthDate)
                    .append(SSN)
                    .append(driverType)
                    .append(hireDate)
                    .append(terminationDate)
                    .append(status)
                    .append(paymentOptions)
                    .append(rate)
                    .append(primaryPhone)
                    .append(primaryPhoneType)
                    .append(altPhone)
                    .append(altPhoneType)
                    .append(fax)
                    .append(email)
                    .append(streetAddress)
                    .append(secondStreetAddress)
                    .append(city)
                    .append(state)
                    .append(zip)
                    .append(licenseNumber)
                    .append(licenseExpiration)
                    .append(licenseDateIssued)
                    .append(licenseStateIssue)
                    .append(licenseClass)
                    .append(licenseEndorsements)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("firstName", firstName)
                    .append("lastName", lastName)
                    .append("birthDate", birthDate)
                    .append("SSN", SSN)
                    .append("driverType", driverType)
                    .append("hireDate", hireDate)
                    .append("terminationDate", terminationDate)
                    .append("status", status)
                    .append("paymentOptions", paymentOptions)
                    .append("rate", rate)
                    .append("primaryPhone", primaryPhone)
                    .append("primaryPhoneType", primaryPhoneType)
                    .append("altPhone", altPhone)
                    .append("altPhoneType", altPhoneType)
                    .append("fax", fax)
                    .append("email", email)
                    .append("streetAddress", streetAddress)
                    .append("secondStreetAddress", secondStreetAddress)
                    .append("city", city)
                    .append("state", state)
                    .append("zip", zip)
                    .append("licenseNumber", licenseNumber)
                    .append("licenseExpiration", licenseExpiration)
                    .append("licenseDateIssued", licenseDateIssued)
                    .append("licenseStateIssue", licenseStateIssue)
                    .append("licenseClass", licenseClass)
                    .append("licenseEndorsements", licenseEndorsements)
                    .toString();
        }
    }

    @Immutable
    final class DriverInformationChanged implements DriverEvent{
        public Optional<String> firstName;
        public Optional<String> lastName;
        public Optional<Date> birthDate;
        public Optional<String> SSN;

        public Optional<DriverType> driverType;
        public Optional<Date> hireDate;
        public Optional<Date> terminationDate;
        public Optional<EmploymentStatus> status;

        public Optional<PaymentOptions> paymentOptions;
        public Optional<Double> rate;

        public Optional<String> primaryPhone;
        public Optional<PhoneType> primaryPhoneType;
        public Optional<String> altPhone;
        public Optional<PhoneType> altPhoneType;
        public Optional<String> fax;
        public Optional<String> email;

        public Optional<String> streetAddress;
        public Optional<String> secondStreetAddress;
        public Optional<String> city;
        public Optional<String> state;
        public Optional<String> zip;

        public Optional<String> licenseNumber;
        public Optional<Date> licenseExpiration;
        public Optional<Date> licenseDateIssued;
        public Optional<LicenseStateIssue> licenseStateIssue;
        public Optional<LicenseClass> licenseClass;
        public Optional<LicenseEndorsements> licenseEndorsements;

        public DriverInformationChanged() {
        }

        public DriverInformationChanged(Optional<String> firstName, Optional<String> lastName, Optional<Date> birthDate, Optional<String> SSN, Optional<DriverType> driverType, Optional<Date> hireDate, Optional<Date> terminationDate, Optional<EmploymentStatus> status, Optional<PaymentOptions> paymentOptions, Optional<Double> rate, Optional<String> primaryPhone, Optional<PhoneType> primaryPhoneType, Optional<String> altPhone, Optional<PhoneType> altPhoneType, Optional<String> fax, Optional<String> email, Optional<String> streetAddress, Optional<String> secondStreetAddress, Optional<String> city, Optional<String> state, Optional<String> zip, Optional<String> licenseNumber, Optional<Date> licenseExpiration, Optional<Date> licenseDateIssued, Optional<LicenseStateIssue> licenseStateIssue, Optional<LicenseClass> licenseClass, Optional<LicenseEndorsements> licenseEndorsements) {

            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.SSN = SSN;
            this.driverType = driverType;
            this.hireDate = hireDate;
            this.terminationDate = terminationDate;
            this.status = status;
            this.paymentOptions = paymentOptions;
            this.rate = rate;
            this.primaryPhone = primaryPhone;
            this.primaryPhoneType = primaryPhoneType;
            this.altPhone = altPhone;
            this.altPhoneType = altPhoneType;
            this.fax = fax;
            this.email = email;
            this.streetAddress = streetAddress;
            this.secondStreetAddress = secondStreetAddress;
            this.city = city;
            this.state = state;
            this.zip = zip;
            this.licenseNumber = licenseNumber;
            this.licenseExpiration = licenseExpiration;
            this.licenseDateIssued = licenseDateIssued;
            this.licenseStateIssue = licenseStateIssue;
            this.licenseClass = licenseClass;
            this.licenseEndorsements = licenseEndorsements;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            DriverInformationChanged that = (DriverInformationChanged) o;

            return new EqualsBuilder()
                    .append(firstName, that.firstName)
                    .append(lastName, that.lastName)
                    .append(birthDate, that.birthDate)
                    .append(SSN, that.SSN)
                    .append(driverType, that.driverType)
                    .append(hireDate, that.hireDate)
                    .append(terminationDate, that.terminationDate)
                    .append(status, that.status)
                    .append(paymentOptions, that.paymentOptions)
                    .append(rate, that.rate)
                    .append(primaryPhone, that.primaryPhone)
                    .append(primaryPhoneType, that.primaryPhoneType)
                    .append(altPhone, that.altPhone)
                    .append(altPhoneType, that.altPhoneType)
                    .append(fax, that.fax)
                    .append(email, that.email)
                    .append(streetAddress, that.streetAddress)
                    .append(secondStreetAddress, that.secondStreetAddress)
                    .append(city, that.city)
                    .append(state, that.state)
                    .append(zip, that.zip)
                    .append(licenseNumber, that.licenseNumber)
                    .append(licenseExpiration, that.licenseExpiration)
                    .append(licenseDateIssued, that.licenseDateIssued)
                    .append(licenseStateIssue, that.licenseStateIssue)
                    .append(licenseClass, that.licenseClass)
                    .append(licenseEndorsements, that.licenseEndorsements)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(firstName)
                    .append(lastName)
                    .append(birthDate)
                    .append(SSN)
                    .append(driverType)
                    .append(hireDate)
                    .append(terminationDate)
                    .append(status)
                    .append(paymentOptions)
                    .append(rate)
                    .append(primaryPhone)
                    .append(primaryPhoneType)
                    .append(altPhone)
                    .append(altPhoneType)
                    .append(fax)
                    .append(email)
                    .append(streetAddress)
                    .append(secondStreetAddress)
                    .append(city)
                    .append(state)
                    .append(zip)
                    .append(licenseNumber)
                    .append(licenseExpiration)
                    .append(licenseDateIssued)
                    .append(licenseStateIssue)
                    .append(licenseClass)
                    .append(licenseEndorsements)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("firstName", firstName)
                    .append("lastName", lastName)
                    .append("birthDate", birthDate)
                    .append("SSN", SSN)
                    .append("driverType", driverType)
                    .append("hireDate", hireDate)
                    .append("terminationDate", terminationDate)
                    .append("status", status)
                    .append("paymentOptions", paymentOptions)
                    .append("rate", rate)
                    .append("primaryPhone", primaryPhone)
                    .append("primaryPhoneType", primaryPhoneType)
                    .append("altPhone", altPhone)
                    .append("altPhoneType", altPhoneType)
                    .append("fax", fax)
                    .append("email", email)
                    .append("streetAddress", streetAddress)
                    .append("secondStreetAddress", secondStreetAddress)
                    .append("city", city)
                    .append("state", state)
                    .append("zip", zip)
                    .append("licenseNumber", licenseNumber)
                    .append("licenseExpiration", licenseExpiration)
                    .append("licenseDateIssued", licenseDateIssued)
                    .append("licenseStateIssue", licenseStateIssue)
                    .append("licenseClass", licenseClass)
                    .append("licenseEndorsements", licenseEndorsements)
                    .toString();
        }
    }

    @Immutable
    final class DriverDisabled implements DriverEvent{
        public Optional<String> reason;

        public DriverDisabled() {
        }

        public DriverDisabled(Optional<String> reason) {
            this.reason = reason;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            DriverDisabled that = (DriverDisabled) o;

            return new EqualsBuilder()
                    .append(reason, that.reason)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(reason)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("reason", reason)
                    .toString();
        }
    }
}
