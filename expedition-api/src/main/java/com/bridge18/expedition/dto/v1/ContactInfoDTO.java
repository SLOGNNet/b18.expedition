package com.bridge18.expedition.dto.v1;

import com.bridge18.expedition.entities.driver.ContactInfoType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

import javax.annotation.concurrent.Immutable;

@Immutable
@EqualsAndHashCode
public class ContactInfoDTO {
    public String label;

    public String value;

    public ContactInfoType type;

    @JsonCreator
    public ContactInfoDTO(@JsonProperty("label") String label,
                          @JsonProperty("value") String value,
                          @JsonProperty("type") ContactInfoType type) {
        this.label = label;
        this.value = value;
        this.type = type;
    }
}
