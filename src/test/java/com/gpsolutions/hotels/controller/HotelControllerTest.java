    package com.gpsolutions.hotels.controller;

    import com.gpsolutions.hotels.dto.AmenitiesDto;
    import com.gpsolutions.hotels.dto.HotelFullDto;
    import com.gpsolutions.hotels.dto.HotelShortDto;
    import com.gpsolutions.hotels.service.impl.HotelServiceImpl;
    import com.gpsolutions.hotels.utils.HotelTestData;
    import jakarta.persistence.EntityNotFoundException;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
    import org.springframework.test.context.bean.override.mockito.MockitoBean;
    import org.springframework.web.context.WebApplicationContext;

    import java.util.List;
    import java.util.Set;

    import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
    import static io.restassured.module.mockmvc.RestAssuredMockMvc.webAppContextSetup;
    import static org.hamcrest.Matchers.*;
    import static org.mockito.ArgumentMatchers.eq;
    import static org.mockito.Mockito.*;
    import static org.mockito.Mockito.any;

    @WebMvcTest(HotelController.class)
    @FieldDefaults(makeFinal = false)
    @RequiredArgsConstructor
    class HotelControllerTest {

        @MockitoBean
        HotelServiceImpl hotelService;
        final WebApplicationContext context;

        HotelFullDto hotelFullDtoForTesting;
        HotelShortDto hotelShortDtoForTestingFirst;
        List<HotelShortDto> hotelShortDtoForTestingList;
        AmenitiesDto amenities;

        @BeforeEach
        void setUp() {
            webAppContextSetup(context);
            hotelFullDtoForTesting = HotelTestData.createFirstFullDto();
            hotelShortDtoForTestingFirst = HotelTestData.createFirstShortDto();
            hotelShortDtoForTestingList = HotelTestData.createShortDtoList();
            amenities = new AmenitiesDto(Set.of("testAmenity1"));
        }

        @Test
        void getHotels_Success() {
            when(hotelService.getAll()).thenReturn(hotelShortDtoForTestingList);

            given()
                    .when()
                    .get("/property-view/hotels")
                    .then()
                    .statusCode(200)
                    .body("$.size()", is(hotelShortDtoForTestingList.size()))
                    .body("[0].id", equalTo(1))
                    .body("[0].name", equalTo("testName1"))
                    .body("[0].description", equalTo("testDescription1"))
                    .body("[0].address", equalTo("1 testStreet1, testCity1, testPostCode1, testCountry1"))
                    .body("[0].phone", equalTo("testPhone1"))
                    .body("[1].id", equalTo(2))
                    .body("[1].name", equalTo("testName2"));
        }

        @Test
        void getHotelById_Success() {
            when(hotelService.getById(1L)).thenReturn(hotelFullDtoForTesting);

            given()
                    .when()
                    .get("/property-view//hotels/{id}", 1L)
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(1))
                    .body("name", equalTo("testName1"))
                    .body("description", equalTo("testDescription1"))
                    .body("brand", equalTo("testBrand1"))
                    .body("address.street", equalTo("testStreet1"))
                    .body("address.city", equalTo("testCity1"))
                    .body("address.country", equalTo("testCountry1"))
                    .body("address.postCode", equalTo("testPostCode1"))
                    .body("contacts.phone", equalTo("testPhone1"))
                    .body("contacts.email", equalTo("testEmail1"))
                    .body("arrivalTime.checkIn", equalTo("testCheckIn1"))
                    .body("arrivalTime.checkOut", equalTo("testCheckOut1"));
        }

        @Test
        void getHotelById_NotFound() {
            when(hotelService.getById(999L))
                    .thenThrow(new EntityNotFoundException("Hotel with id 999 not found"));

            given()
                    .when()
                    .get("/property-view/hotels/{id}", 999L)
                    .then()
                    .statusCode(404)
                    .body("message", equalTo("Hotel with id 999 not found"));
        }

        @Test
        void searchHotels_Success() {
            hotelShortDtoForTestingList = List.of(hotelShortDtoForTestingFirst);
            when(hotelService.search("testName1", null, null, null, null))
                    .thenReturn(hotelShortDtoForTestingList);

            given()
                    .queryParam("name", "testName1")
                    .when()
                    .get("/property-view/hotels/search")
                    .then()
                    .statusCode(200)
                    .body("$.size()", is(1))
                    .body("[0].id", equalTo(1))
                    .body("[0].name", equalTo("testName1"))
                    .body("[0].description", equalTo("testDescription1"))
                    .body("[0].address", equalTo("1 testStreet1, testCity1, testPostCode1, testCountry1"))
                    .body("[0].phone", equalTo("testPhone1"));
        }

        @Test
        void createHotel_Success() {
            when(hotelService.create(hotelFullDtoForTesting)).thenReturn(hotelShortDtoForTestingFirst);

            given()
                    .contentType("application/json")
                    .body(hotelFullDtoForTesting)
                    .when()
                    .post("/property-view/hotels")
                    .then()
                    .statusCode(201)
                    .body("id", equalTo(1))
                    .body("name", equalTo("testName1"))
                    .body("description", equalTo("testDescription1"))
                    .body("address", equalTo("1 testStreet1, testCity1, testPostCode1, testCountry1"))
                    .body("phone", equalTo("testPhone1"));
        }

        @Test
        void createHotel_ValidationError() {
            HotelFullDto invalidHotelDto = new HotelFullDto(null,null,null,null,null,null, null, null);
            given()
                    .contentType("application/json")
                    .body(invalidHotelDto)
                    .when()
                    .post("/property-view/hotels")
                    .then()
                    .statusCode(400)
                    .body(containsString("Name is required"));
        }


        @Test
        void addAmenities_Success() {
            given()
                    .contentType("application/json")
                    .body(amenities)
                    .when()
                    .post("/property-view/hotels/{id}/amenities", 1)
                    .then()
                    .statusCode(204);

            verify(hotelService).addAmenities(eq(1L), eq(amenities));
        }

        @Test
        void addAmenities_ValidationError() {
            AmenitiesDto invalidAmenities = new AmenitiesDto(Set.of());

            given()
                    .contentType("application/json")
                    .body(invalidAmenities)
                    .when()
                    .post("/property-view/hotels/{id}/amenities", 1)
                    .then()
                    .statusCode(400)
                    .body("message", containsString("Amenities is required"));
        }


        @Test
        void addAmenities_NotFound() {
            doThrow(new EntityNotFoundException("Hotel with id 1 not found"))
                    .when(hotelService).addAmenities(eq(1L), any());

            given()
                    .contentType("application/json")
                    .body(amenities)
                    .when()
                    .post("/property-view/hotels/{id}/amenities", 1)
                    .then()
                    .statusCode(404)
                    .body("message", containsString("not found"));
        }
    }