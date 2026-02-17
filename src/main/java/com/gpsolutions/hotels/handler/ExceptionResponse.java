package com.gpsolutions.hotels.handler;

import java.time.LocalDateTime;


public record ExceptionResponse(String message, LocalDateTime timestamp) {
}
