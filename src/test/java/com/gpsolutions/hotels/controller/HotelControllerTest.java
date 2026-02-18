package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.service.Impl.HotelServiceImpl;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(HotelController.class)
@FieldDefaults(makeFinal = false)
class HotelControllerTest {

    @InjectMocks
    HotelServiceImpl hotelService;

    @Test
    void getHotels() {
        Mockito.when(hotelService.getAll()).thenReturn()
    }

    @Test
    void getHotelById() {
    }

    @Test
    void searchHotels() {
    }

    @Test
    void createHotel() {
    }

    @Test
    void addAmenities() {
    }
}