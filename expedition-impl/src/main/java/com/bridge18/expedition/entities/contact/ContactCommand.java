package com.bridge18.expedition.entities.contact;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;

import javax.annotation.concurrent.Immutable;
import java.util.Optional;

public interface ContactCommand extends Jsonable {

    @Immutable
    @JsonDeserialize
    final class ContactCreated implements ContactCommand, CompressedJsonable, PersistentEntity.ReplyType<ContactState> {
        public ContactCreated() {
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof ContactCreated) {
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
    final class AddContactDetails implements ContactCommand,CompressedJsonable,
            PersistentEntity.ReplyType<ContactState>{

        public Optional<String> firstName;
        public Optional<String> middleName;
        public Optional<String> lastName;
        public Optional<String> getPosition;
        public Optional<String> getAddress;

        public AddContactDetails( Optional<String> firstName, Optional<String> middleName,
                                 Optional<String> lastName, Optional<String> getPosition, Optional<String> getAddress) {

            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.getPosition = getPosition;
            this.getAddress = getAddress;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AddContactDetails that = (AddContactDetails) o;

            if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
            if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
            if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
            if (getPosition != null ? !getPosition.equals(that.getPosition) : that.getPosition != null) return false;
            return getAddress != null ? getAddress.equals(that.getAddress) : that.getAddress == null;

        }

        @Override
        public int hashCode() {
            int result = firstName != null ? firstName.hashCode() : 0;
            result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
            result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
            result = 31 * result + (getPosition != null ? getPosition.hashCode() : 0);
            result = 31 * result + (getAddress != null ? getAddress.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "AddContactDetails{" +
                    "firstName=" + firstName +
                    ", middleName=" + middleName +
                    ", lastName=" + lastName +
                    ", getPosition=" + getPosition +
                    ", getAddress=" + getAddress +
                    '}';
        }
    }


}
