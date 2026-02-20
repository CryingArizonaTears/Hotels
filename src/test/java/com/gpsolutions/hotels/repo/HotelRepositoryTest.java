package com.gpsolutions.hotels.repo;

import com.gpsolutions.hotels.entity.Hotel;
import com.gpsolutions.hotels.utils.HotelTestData;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestConstructor;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class HotelRepositoryTest {

    HotelRepository hotelRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:alpine3.22")
                    .withDatabaseName("testhotels")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @BeforeEach
    void setUp() {
        hotelRepository.deleteAll();

        Hotel firstHotel = HotelTestData.createFirstHotel();
        firstHotel.setAmenities(List.of("testAmenity1", "testAmenity2"));
        firstHotel.setId(null);

        Hotel secondHotel = HotelTestData.createFirstHotel();
        secondHotel.setAmenities(List.of("testAmenity1", "testAmenity2"));
        secondHotel.setId(null);

        Hotel thirdHotel = HotelTestData.createSecondHotel();
        thirdHotel.setAmenities(List.of("testAmenity3", "testAmenity4"));
        thirdHotel.setId(null);

        hotelRepository.saveAll(List.of(firstHotel, secondHotel, thirdHotel));
    }

    @Test
    void countByBrand_Success() {
        List<Object[]> result = hotelRepository.countByBrand();

        assertThat(result).isNotEmpty();
        assertThat(result)
                .anyMatch(r -> r[0].equals("testBrand1") && ((Number) r[1]).intValue() == 2)
                .anyMatch(r -> r[0].equals("testBrand2") && ((Number) r[1]).intValue() == 1);
    }

    @Test
    void countByCity_Success() {
        List<Object[]> result = hotelRepository.countByCity();

        assertThat(result).isNotEmpty();
        assertThat(result)
                .anyMatch(r -> r[0].equals("testCity1") && ((Number) r[1]).intValue() == 2)
                .anyMatch(r -> r[0].equals("testCity2") && ((Number) r[1]).intValue() == 1);
    }

    @Test
    void countByCountry_Success() {
        List<Object[]> result = hotelRepository.countByCountry();

        assertThat(result).isNotEmpty();
        assertThat(result)
                .anyMatch(r -> r[0].equals("testCountry1") && ((Number) r[1]).intValue() == 2)
                .anyMatch(r -> r[0].equals("testCountry2") && ((Number) r[1]).intValue() == 1);
    }

    @Test
    void countByAmenities_Success() {
        List<Object[]> result = hotelRepository.countByAmenities();

        assertThat(result).isNotEmpty();
        assertThat(result)
                .anyMatch(r -> r[0].equals("testAmenity1") && ((Number) r[1]).intValue() == 2)
                .anyMatch(r -> r[0].equals("testAmenity2") && ((Number) r[1]).intValue() == 2)
                .anyMatch(r -> r[0].equals("testAmenity3") && ((Number) r[1]).intValue() == 1)
                .anyMatch(r -> r[0].equals("testAmenity4") && ((Number) r[1]).intValue() == 1);
    }
}