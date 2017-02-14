/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.bridge18.expedition.entities.load;

import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import com.bridge18.expedition.entities.FreightType;
import com.bridge18.expedition.entities.LoadType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;

public interface LoadCommand extends Jsonable {

    @Immutable
    @JsonDeserialize
    public final class CreateLoad implements LoadCommand, CompressedJsonable, PersistentEntity.ReplyType<LoadState> {
        public CreateLoad() {
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof CreateLoad) {
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
    public final class AddLoadDetails implements LoadCommand, CompressedJsonable, PersistentEntity.ReplyType<LoadState> {
        public Optional<String> customerId;
        public Optional<String> customerAddressId;
        public Optional<String> carrierLoadNumber;
        public Optional<String> brokerLoadNumber;

        public Optional<LoadType> loadType;
        public Optional<FreightType> freightType;

        public AddLoadDetails() {
        }

        public AddLoadDetails(Optional<String> customerId, Optional<String> customerAddressId,
                              Optional<String> carrierLoadNumber, Optional<String> brokerLoadNumber,
                              Optional<LoadType> loadType, Optional<FreightType> freightType) {

            this.customerId = customerId;
            this.customerAddressId = customerAddressId;
            this.carrierLoadNumber = carrierLoadNumber;
            this.brokerLoadNumber = brokerLoadNumber;
            this.loadType = loadType;
            this.freightType = freightType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AddLoadDetails that = (AddLoadDetails) o;

            if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
            if (customerAddressId != null ? !customerAddressId.equals(that.customerAddressId) : that.customerAddressId != null)
                return false;
            if (carrierLoadNumber != null ? !carrierLoadNumber.equals(that.carrierLoadNumber) : that.carrierLoadNumber != null)
                return false;
            if (brokerLoadNumber != null ? !brokerLoadNumber.equals(that.brokerLoadNumber) : that.brokerLoadNumber != null)
                return false;
            if (loadType != null ? !loadType.equals(that.loadType) : that.loadType != null) return false;
            return freightType != null ? freightType.equals(that.freightType) : that.freightType == null;
        }

        @Override
        public int hashCode() {
            int result = customerId != null ? customerId.hashCode() : 0;
            result = 31 * result + (customerAddressId != null ? customerAddressId.hashCode() : 0);
            result = 31 * result + (carrierLoadNumber != null ? carrierLoadNumber.hashCode() : 0);
            result = 31 * result + (brokerLoadNumber != null ? brokerLoadNumber.hashCode() : 0);
            result = 31 * result + (loadType != null ? loadType.hashCode() : 0);
            result = 31 * result + (freightType != null ? freightType.hashCode() : 0);
            return result;
        }
    }


}
