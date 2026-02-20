package com.gpsolutions.hotels.controller;

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
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelController {

    HotelService hotelService;

    @GetMapping("/hotels")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение списка всех отелей с их краткой информацией")
    @ApiResponse(responseCode = "200", description = "Список отелей получен", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HotelShortDto.class)), examples = @ExampleObject(value = """
             [
             	{
            		"id": 1,
            		"name": "DoubleTree by Hilton Minsk",
            		"description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel`s 20th floor ...",
            		"address": "9 Pobediteley Avenue, Minsk, 220004, Belarus",
            		"phone": "+375 17 309-80-00"
            	}
            ]
            """)))
    @ApiResponse(responseCode = "404", description = "Отели не найдены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    public List<HotelShortDto> getHotels() {
        return hotelService.getAll();
    }

    @GetMapping("/hotels/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение расширенной информации по конкретному отелю", responses = {@ApiResponse(responseCode = "200", description = "Расширенная информация получена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelFullDto.class, example = """
            {
             			"id": 1,
             			"name": "DoubleTree by Hilton Minsk",
                        "description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel`s 20th floor ...",
             			"brand": "Hilton",
             			"address":
             				{
             					"houseNumber": 9,
             					"street": "Pobediteley Avenue",
             					"city": "Minsk",
             					"country": "Belarus",
             					"postCode": "220004"
             				},
             			"contacts":
             				{
             					"phone": "+375 17 309-80-00",
             					"email": "doubletreeminsk.info@hilton.com"
             				},
             			"arrivalTime":
             				{
             					"checkIn": "14:00",
             					"checkOut": "12:00"
             				},
             			"amenities":
             				[
             					"Free parking",
             					"Free WiFi",
             					"Non-smoking rooms",
             					"Concierge",
             					"On-site restaurant",
             					"Fitness center",
             					"Pet-friendly rooms",
             					"Room service",
             					"Business center",
             					"Meeting rooms"
             				]
             		}
            """))), @ApiResponse(responseCode = "404", description = "Отель не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))})
    public HotelFullDto getHotelById(@PathVariable Long id) {
        return hotelService.getById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение списка всех отелей с их краткой информацией по следующим параметрам:" + " name, brand, city, country, amenities")
    @ApiResponse(responseCode = "200", description = "Список отелей получен", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HotelShortDto.class)), examples = @ExampleObject(value = """
            [
            	{
            		"id": 1,
            		"name": "DoubleTree by Hilton Minsk",
            		"description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel`s 20th floor ...",
            		"address": "9 Pobediteley Avenue, Minsk, 220004, Belarus",
            		"phone": "+375 17 309-80-00"
            	}
            ]
            """)))
    public List<HotelShortDto> searchHotels(@RequestParam(required = false) String name, @RequestParam(required = false) String brand, @RequestParam(required = false) String city, @RequestParam(required = false) String country, @RequestParam(required = false) String amenities) {
        return hotelService.search(name, brand, city, country, amenities);
    }

    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового отеля", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные нового отеля", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelFullDto.class), examples = {@ExampleObject(name = "Пример создания отеля", value = """
            {
              "name": "DoubleTree by Hilton Minsk",
              "description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel`s 20th floor ...",
              "brand": "Hilton",
              "address":
                {
                  "houseNumber": 9,
                  "street": "Pobediteley Avenue",
                  "city": "Minsk",
                  "country": "Belarus",
                  "postCode": "220004"
                },
              "contacts":
                {
                  "phone": "+375 17 309-80-00",
                  "email": "doubletreeminsk.info@hilton.com"
                },
              "arrivalTime":
                {
                  "checkIn": "14:00",
                  "checkOut": "12:00"
                }
            }
            """)})), responses = {@ApiResponse(responseCode = "201", description = "Отель создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelShortDto.class, example = """
            {
             	"id": 1,
             	"name": "DoubleTree by Hilton Minsk",
             	"description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel`s 20th floor ...",
             	"address": "9 Pobediteley Avenue, Minsk, 220004, Belarus",
             	"phone": "+375 17 309-80-00"
            }
            """))), @ApiResponse(responseCode = "400", description = "Валидация провалена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))})
    public HotelShortDto createHotel(@Valid @RequestBody HotelFullDto hotelDto) {
        return hotelService.create(hotelDto);
    }

    @PostMapping("/hotels/{id}/amenities")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Добавление списка удобств к отелю", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Список удобств для добавления к отелю", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = {@ExampleObject(name = "Пример добавления удобств", value = """
            [
            	"Free parking",
            	"Free WiFi",
            	"Non-smoking rooms",
            	"Concierge",
            	"On-site restaurant",
            	"Fitness center",
            	"Pet-friendly rooms",
            	"Room service",
            	"Business center",
            	"Meeting rooms"
            ]
            """)})), responses = {@ApiResponse(responseCode = "204", description = "Список удобств добавлен"), @ApiResponse(responseCode = "400", description = "Валидация провалена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))), @ApiResponse(responseCode = "404", description = "Отель не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))})
    public void addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        hotelService.addAmenities(id, amenities);
    }

}
