package com.bridge18.expedition.dto.v1;

public final class DriverSummary {
    String id;
    String firstName;
    String lastName;

    public DriverSummary(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
