package com.gpsolutions.hotels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record AmenitiesDto(
        @NotEmpty(message = "Amenities is required")
        Set<@NotBlank(message = "Amenity name is required") @Size(max = 255) String> amenities
) {
}
