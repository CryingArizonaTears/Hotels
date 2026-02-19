package com.gpsolutions.hotels.service.impl;

import com.gpsolutions.hotels.repo.HotelRepository;
import com.gpsolutions.hotels.service.HotelAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelAnalyticsServiceImpl implements HotelAnalyticsService {

    HotelRepository hotelRepository;

    @Override
    public Map<String, Long> histogramBy(String param) {
        List<Object[]> results = switch (param.toLowerCase()) {
            case "brand" -> hotelRepository.countByBrand();
            case "city" -> hotelRepository.countByCity();
            case "country" -> hotelRepository.countByCountry();
            case "amenities" -> hotelRepository.countByAmenities();
            default -> throw new IllegalArgumentException("Unknown histogram parameter: " + param);
        };
        if (results.isEmpty()) {
            return Map.of(param, 0L);
        }
        return results.stream().collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));
    }
}
