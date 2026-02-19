package com.gpsolutions.hotels.service.Impl;

import com.gpsolutions.hotels.dto.AmenitiesDto;
import com.gpsolutions.hotels.dto.HotelFullDto;
import com.gpsolutions.hotels.dto.HotelShortDto;
import com.gpsolutions.hotels.entity.Hotel;
import com.gpsolutions.hotels.mapper.HotelMapper;
import com.gpsolutions.hotels.repo.HotelRepository;
import com.gpsolutions.hotels.utils.HotelTestData;
import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(makeFinal = false)
class HotelServiceImplTest {

    @Mock
    HotelRepository hotelRepository;
    HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);
    HotelServiceImpl hotelServiceImpl;
    Hotel hotelForTestingFirst;
    Hotel hotelForTestingSecond;
    HotelFullDto hotelFullDtoForTesting;
    HotelShortDto hotelShortDtoForTestingFirst;
    List<HotelShortDto> hotelShortDtoForTestingList;

    @BeforeEach
    void setUp() {
        hotelServiceImpl = new HotelServiceImpl(hotelRepository, hotelMapper);

        hotelForTestingFirst = HotelTestData.createFirstHotel();
        hotelForTestingSecond = HotelTestData.createSecondHotel();
        hotelFullDtoForTesting = HotelTestData.createFirstFullDto();
        hotelShortDtoForTestingFirst = HotelTestData.createFirstShortDto();
        hotelShortDtoForTestingList = HotelTestData.createShortDtoList();
    }

    @Test
    void getAll_Success() {
        List<Hotel> hotelForTestingList = List.of(hotelForTestingFirst, hotelForTestingSecond);
        when(hotelRepository.findAll()).thenReturn(hotelForTestingList);
        var result = hotelServiceImpl.getAll();
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(hotelShortDtoForTestingList);
    }

    @Test
    void getById_Success() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.ofNullable(hotelForTestingFirst));
        var result = hotelServiceImpl.getById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(hotelFullDtoForTesting);
    }

    @Test
    void getById_NotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> hotelServiceImpl.getById(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void search_Success() {
        hotelForTestingFirst.setAmenities(Set.of("testAmenity1", "testAmenity2"));
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotelForTestingFirst));

        var resultName = hotelServiceImpl.search("testName1", null, null, null, null);
        assertThat(resultName).isNotNull();
        assertThat(resultName.getFirst()).isEqualTo(hotelShortDtoForTestingFirst);

        var resultBrand = hotelServiceImpl.search(null, "testBrand1", null, null, null);
        assertThat(resultBrand).isNotNull();
        assertThat(resultBrand.getFirst()).isEqualTo(hotelShortDtoForTestingFirst);

        var resultCity = hotelServiceImpl.search(null, null, "testCity1", null, null);
        assertThat(resultCity).isNotNull();
        assertThat(resultCity.getFirst()).isEqualTo(hotelShortDtoForTestingFirst);

        var resultCountry = hotelServiceImpl.search(null, null, null, "testCountry1", null);
        assertThat(resultCountry).isNotNull();
        assertThat(resultCountry.getFirst()).isEqualTo(hotelShortDtoForTestingFirst);

        var resultAmenities = hotelServiceImpl.search(null, null, null, null, "testAmenity1");
        assertThat(resultAmenities).isNotNull();
        assertThat(resultAmenities.getFirst()).isEqualTo(hotelShortDtoForTestingFirst);
    }

    @Test
    void create_Success() {
        when(hotelRepository.save(any())).thenReturn(hotelForTestingFirst);
        var result = hotelServiceImpl.create(hotelFullDtoForTesting);
        assertThat(result).isEqualTo(hotelShortDtoForTestingFirst);
    }

    @Test
    void addAmenities_Success() {
        AmenitiesDto amenitiesDtoForTesting = new AmenitiesDto(Set.of("testAmenity1", "testAmenity2"));
        when(hotelRepository.findById(any())).thenReturn(Optional.ofNullable(hotelForTestingFirst));
        hotelServiceImpl.addAmenities(1L, amenitiesDtoForTesting);
        verify(hotelRepository).save(argThat(hotel -> hotel.getAmenities().contains("testAmenity1") && hotel.getAmenities().contains("testAmenity2")));
    }

    @Test
    void addAmenities_NotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() ->
                hotelServiceImpl.addAmenities(1L, new AmenitiesDto(Set.of()))
        ).isInstanceOf(EntityNotFoundException.class);
    }
}