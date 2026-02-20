package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.handler.ExceptionResponse;
import com.gpsolutions.hotels.service.HotelAnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelAnalyticsController {

    HotelAnalyticsService hotelAnalyticsService;

    @GetMapping("histogram/{param}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получение колличества отелей сгруппированных по каждому значению указанного параметра. " +
                    "Параметр: brand, city, country, amenities.",
            parameters = {
                    @Parameter(
                            name = "param",
                            description = "Параметр для группировки: brand, city, country, amenities",
                            example = "brand",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список отелей получен",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "{\"brand\": 10, \"city\": 5}")
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Неверный параметр", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelAnalyticsService.histogramBy(param);
    }

}
