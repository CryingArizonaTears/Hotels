package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.handler.ExceptionResponse;
import com.gpsolutions.hotels.service.HotelAnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/property-view/hotels/histogram")
@RequiredArgsConstructor
public class HotelAnalyticsController {

    HotelAnalyticsService hotelAnalyticsService;

    @GetMapping("/{param}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получение колличества отелей сгруппированных по каждому значению указанного параметра. " +
                    "Параметр: brand, city, country, amenities.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список отелей получен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный параметр", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelAnalyticsService.histogramBy(param);
    }

}
