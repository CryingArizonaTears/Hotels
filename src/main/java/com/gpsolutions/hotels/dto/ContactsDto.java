package com.gpsolutions.hotels.dto;

import jakarta.validation.constraints.Size;

public record ContactsDto(
        @Size(max = 50)
        String phone,
        @Size(max = 50)
        String email
) {
}