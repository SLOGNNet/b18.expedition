package com.bridge18.expedition.repository.mongo;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

//@Entity("drivers")
public class Driver {
    @Id
    private String id;

    @Indexed
    private String driverId;
    private String firstName;
    private String lastName;

    public Driver(String id, String driverId, String firstName, String lastName) {
        this.id = id;
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Driver(String driverId, String firstName, String lastName) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
