package com.bridge18.expedition.dto.v1;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddressDTO {
    public Integer id;
    public String name;
    public String streetAddress1;
    public String streetAddress2;
    public String city;
    public String phone;
    public String state;
    public String zip;
    public String fax;
    public String phoneExtension;
    public String faxExtension;
    public Double latitude;
    public Double longitude;
}
