package com.gpsolutions.hotels.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
    Integer houseNumber;
    String street;
    String city;
    String country;
    String postCode;
}
