package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.service.HotelAnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/property-view/hotels/histogram")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class HotelAnalyticsController {

    HotelAnalyticsService hotelAnalyticsService;

    @GetMapping("/{param}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> getHistogram(@PathVariable String param) {
        return hotelAnalyticsService.histogramBy(param);
    }

}
