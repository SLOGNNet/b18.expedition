package com.bridge18.expedition.dto.v1;


public class ContactDTO {
    public Integer id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String contactInfo;
    public String position;
    public AddressDTO address;

    public ContactDTO(Integer id, String firstName, String middleName, String lastName, String contactInfo, String position, AddressDTO address) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.contactInfo = contactInfo;
        this.position = position;
        this.address = address;
    }
}
