package com.bridge18.expedition.entities.contact;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;
import java.util.Optional;

public interface ContactEvent {

    @Immutable
    @JsonDeserialize
    final class ContactCreated implements ContactEvent {
        public ContactCreated() {
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ContactCommand.ContactCreated) {
                return true;
            }
            return super.equals(obj);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }


    @Immutable
    @JsonDeserialize
    final class ContactDetailsAdded implements ContactEvent {

        public Optional<String> firstName;
        public Optional<String> middleName;
        public Optional<String> lastName;
        public Optional<String> position;
        public Optional<String> address;

        public ContactDetailsAdded( Optional<String> firstName, Optional<String> middleName,
                                   Optional<String> lastName, Optional<String> getPosition, Optional<String> getAddress) {
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.position = getPosition;
            this.address = getAddress;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ContactDetailsAdded that = (ContactDetailsAdded) o;

            if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
            if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
            if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
            if (position != null ? !position.equals(that.position) : that.position != null) return false;
            return address != null ? address.equals(that.address) : that.address == null;

        }

        @Override
        public int hashCode() {
            int result = firstName != null ? firstName.hashCode() : 0;
            result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
            result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
            result = 31 * result + (position != null ? position.hashCode() : 0);
            result = 31 * result + (address != null ? address.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ContactDetailsAdded{" +
                    "firstName=" + firstName +
                    ", middleName=" + middleName +
                    ", lastName=" + lastName +
                    ", position=" + position +
                    ", address=" + address +
                    '}';
        }
    }

}
