package com.gpsolutions.hotels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record HotelFullDto(
        Long id,
        @NotBlank(message = "Name is required")
        @Size(max = 255)
        String name,
        @Size(max = 1000)
        String description,
        @Size(max = 255)
        String brand,
        AddressDto address,
        ContactsDto contacts,
        ArrivalTimeDto arrivalTime,
        Set<String> amenities
) {
}
