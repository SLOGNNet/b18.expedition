package com.bridge18.expedition.entities.driver;

import lombok.Value;

import java.util.Optional;

@Value
public class ContactInfo {
    Optional<String> label;

    Optional<String> value;

    Optional<ContactInfoType> type;
}
