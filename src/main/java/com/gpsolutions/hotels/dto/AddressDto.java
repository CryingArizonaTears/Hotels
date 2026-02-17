package com.gpsolutions.hotels.dto;

import jakarta.validation.constraints.Size;

public record AddressDto(
        Integer houseNumber,
        @Size(max = 255)
        String street,
        @Size(max = 255)
        String city,
        @Size(max = 255)
        String country,
        @Size(max = 50)
        String postCode
) {
}