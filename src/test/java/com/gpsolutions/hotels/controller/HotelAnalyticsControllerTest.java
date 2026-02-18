package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.service.Impl.HotelAnalyticsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.webAppContextSetup;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebMvcTest(HotelAnalyticsController.class)
@FieldDefaults(makeFinal = false)
@RequiredArgsConstructor
class HotelAnalyticsControllerTest {

    @MockitoBean
    HotelAnalyticsServiceImpl hotelAnalyticsService;
    final WebApplicationContext context;

    @BeforeEach
    void setUp() {
        webAppContextSetup(context);
    }

    @Test
    void getHistogram_Success() {
        Map<String, Long> histogram = Map.of(
                "testBrand1", 1L,
                "testBrand2", 2L
        );

        when(hotelAnalyticsService.histogramBy("brand")).thenReturn(histogram);

        given()
                .contentType("application/json")
                .when()
                .get("/property-view/hotels/histogram/{param}", "brand")
                .then()
                .statusCode(200)
                .body("testBrand1", equalTo(1))
                .body("testBrand2", equalTo(2));
    }

    @Test
    void getHistogram_InvalidParam() {
        when(hotelAnalyticsService.histogramBy("testInvalidParam"))
                .thenThrow(new IllegalArgumentException("Unknown histogram parameter: testInvalidParam"));

        given()
                .contentType("application/json")
                .when()
                .get("/property-view/hotels/histogram/{param}", "testInvalidParam")
                .then()
                .statusCode(400)
                .body("message", equalTo("Unknown histogram parameter: testInvalidParam"));
    }

}