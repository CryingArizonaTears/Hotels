package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.dto.AmenitiesDto;
import com.gpsolutions.hotels.dto.HotelFullDto;
import com.gpsolutions.hotels.dto.HotelShortDto;
import com.gpsolutions.hotels.handler.ExceptionResponse;
import com.gpsolutions.hotels.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-view/hotels")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class HotelController {

    HotelService hotelService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение списка всех отелей с их краткой информацией")
    @ApiResponse(responseCode = "200", description = "Список отелей получен")
    public List<HotelShortDto> getHotels() {
        return hotelService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получение расширенной информации по конкретному отелю",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Расширенная информация получена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "404", description = "Отель не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public HotelFullDto getHotelById(@PathVariable Long id) {
        return hotelService.getById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение списка всех отелей с их краткой информацией по следующим параметрам:" +
            " name, brand, city, country, amenities")
    @ApiResponse(responseCode = "200", description = "Список отелей получен")
    public List<HotelShortDto> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities
    ) {
        return hotelService.search(name, brand, city, country, amenities);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создание нового отеля",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Отель создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "400", description = "Валидация провалена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public HotelShortDto createHotel(@Valid @RequestBody HotelFullDto hotelDto) {
        return hotelService.create(hotelDto);
    }

    @PostMapping("/{id}/amenities")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Добавление списка удобств к отелю",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список удобств добавлен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "400", description = "Валидация провалена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Отель не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public void addAmenities(@PathVariable Long id, @Valid @RequestBody AmenitiesDto amenities) {
        hotelService.addAmenities(id, amenities);
    }

}
