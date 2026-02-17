package com.gpsolutions.hotels.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleApiRequestException(EntityNotFoundException ex) {
        return new ExceptionResponse(
                ex.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleApiRequestException(Exception ex) {
        return new ExceptionResponse(
                ex.getMessage(),
                LocalDateTime.now());
    }
}
