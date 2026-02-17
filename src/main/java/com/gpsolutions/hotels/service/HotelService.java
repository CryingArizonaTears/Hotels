package com.gpsolutions.hotels.service;

import com.gpsolutions.hotels.dto.AmenitiesDto;
import com.gpsolutions.hotels.dto.HotelFullDto;
import com.gpsolutions.hotels.dto.HotelShortDto;

import java.util.List;
import java.util.Set;

public interface HotelService {

    List<HotelShortDto> getAll();

    HotelFullDto getById(Long id);

    List<HotelShortDto> search(String name, String brand, String city, String country, String amenities);

    HotelShortDto create(HotelFullDto hotelFullDto);

    void addAmenities(Long hotelId, AmenitiesDto amenities);

}
