package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.dto.AmenitiesDto;
import com.gpsolutions.hotels.dto.HotelFullDto;
import com.gpsolutions.hotels.dto.HotelShortDto;
import com.gpsolutions.hotels.handler.ExceptionResponse;
import com.gpsolutions.hotels.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-view/hotels")
@RequiredArgsConstructor
public class HotelController {

    HotelService hotelService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение списка всех отелей с их краткой информацией")
    @ApiResponse(responseCode = "200", description = "Список отелей получен", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = HotelShortDto.class)),
            examples = @ExampleObject(value = """
                    [
                      {
                        "id": "testId1",
                        "name": "testName1",
                        "brand": "testBrand1",
                        "city": "testCity1",
                        "country": "testCountry1"
                      },
                      {
                        "id": "testId2",
                        "name": "testName2",
                        "brand": "testBrand2",
                        "city": "testCity2",
                        "country": "testCountry2"
                      }
                    ]
                    """)))
    @ApiResponse(responseCode = "404", description = "Отели не найдены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    public List<HotelShortDto> getHotels() {
        return hotelService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получение расширенной информации по конкретному отелю",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Расширенная информация получена", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelFullDto.class, example = """
                                    {
                                      "id": "testId1",
                                      "name": "testName1",
                                      "description": "testDescription1",
                                      "brand": "testBrand1",
                                      "houseNumber": "testHouseNumber1",
                                      "street": "testStreet1",
                                      "city": "testCity1",
                                      "country": "testCountry1",
                                      "postCode": "testPostCode1",
                                      "phone": "testPhone1",
                                      "email": "testEmail1",
                                      "checkIn": "testCheckIn1",
                                      "checkOut": "testCheckOut1",
                                      "amenities": ["testAmenity1", "testAmenity2"]
                                    }
                                    """))),
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
    @ApiResponse(responseCode = "200", description = "Список отелей получен", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = HotelShortDto.class)),
            examples = @ExampleObject(value = """
                    [
                      {
                        "id": "testId1",
                        "name": "testName1",
                        "brand": "testBrand1",
                        "city": "testCity1",
                        "country": "testCountry1"
                      },
                      {
                        "id": "testId2",
                        "name": "testName2",
                        "brand": "testBrand2",
                        "city": "testCity2",
                        "country": "testCountry2"
                      }
                    ]
                    """)))
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
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные нового отеля",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelFullDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Пример создания отеля",
                                            value = """
                                                    {
                                                      "name": "testName1",
                                                      "description": "testDescription1",
                                                      "brand": "testBrand1",
                                                      "address": {
                                                        "houseNumber": 1,
                                                        "street": "testStreet1",
                                                        "city": "testCity1",
                                                        "country": "testCountry1",
                                                        "postCode": "testPostCode1"
                                                      },
                                                      "contacts": {
                                                        "phone": "testPhone1",
                                                        "email": "testEmail1"
                                                      },
                                                      "arrivalTime": {
                                                        "checkIn": "testCheckIn1",
                                                        "checkOut": "testCheckOut1"
                                                      }
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Отель создан", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelShortDto.class,
                                    example = """
                                            {
                                              "id": "testId1",
                                              "name": "testName1",
                                              "brand": "testBrand1",
                                              "city": "testCity1",
                                              "country": "testCountry1"
                                            }
                                            """))),
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
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Список удобств для добавления к отелю",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AmenitiesDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Пример добавления удобств",
                                            value = """
                                                    {
                                                      "amenities": ["testAmenity1", "testAmenity2"]
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Список удобств добавлен"),
                    @ApiResponse(responseCode = "400", description = "Валидация провалена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Отель не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public void addAmenities(@PathVariable Long id, @Valid @RequestBody AmenitiesDto amenities) {
        hotelService.addAmenities(id, amenities);
    }

}
