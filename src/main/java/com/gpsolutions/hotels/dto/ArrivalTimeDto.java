package com.gpsolutions.hotels.dto;

import jakarta.validation.constraints.Size;

public record ArrivalTimeDto(
        @Size(max = 50)
        String checkIn,
        @Size(max = 50)
        String checkOut
) {
}