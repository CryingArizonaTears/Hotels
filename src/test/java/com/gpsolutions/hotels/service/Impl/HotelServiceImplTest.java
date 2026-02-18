package com.gpsolutions.hotels.service.Impl;

import com.gpsolutions.hotels.dto.*;
import com.gpsolutions.hotels.entity.Address;
import com.gpsolutions.hotels.entity.ArrivalTime;
import com.gpsolutions.hotels.entity.Contacts;
import com.gpsolutions.hotels.entity.Hotel;
import com.gpsolutions.hotels.mapper.HotelMapper;
import com.gpsolutions.hotels.repo.HotelRepository;
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
    Address addressForTestingFirst = new Address();
    ArrivalTime arrivalTimeForTestingFirst = new ArrivalTime();
    Contacts contactsForTestingFirst = new Contacts();
    Hotel hotelForTestingFirst = new Hotel();
    Address addressForTestingSecond = new Address();
    ArrivalTime arrivalTimeForTestingSecond = new ArrivalTime();
    Contacts contactsForTestingSecond = new Contacts();
    Hotel hotelForTestingSecond = new Hotel();
    AddressDto addressDtoForTesting;
    ArrivalTimeDto arrivalTimeDtoForTesting;
    ContactsDto contactsDtoForTesting;
    HotelFullDto hotelFullDtoForTesting;
    HotelShortDto hotelShortDtoForTestingFirst;
    HotelShortDto hotelShortDtoForTestingSecond;
    List<HotelShortDto> hotelShortDtoForTestingList;


    @BeforeEach
    void setUp() {

        hotelServiceImpl = new HotelServiceImpl(hotelRepository, hotelMapper);

        //first hotel
        addressForTestingFirst.setCity("testCity1");
        addressForTestingFirst.setCountry("testCountry1");
        addressForTestingFirst.setStreet("testStreet1");
        addressForTestingFirst.setPostCode("testPostCode1");
        addressForTestingFirst.setHouseNumber(1);

        arrivalTimeForTestingFirst.setCheckIn("testCheckIn1");
        arrivalTimeForTestingFirst.setCheckOut("testCheckOut1");

        contactsForTestingFirst.setEmail("testEmail1");
        contactsForTestingFirst.setPhone("testPhone1");

        hotelForTestingFirst = new Hotel();
        hotelForTestingFirst.setId(1L);
        hotelForTestingFirst.setBrand("testBrand1");
        hotelForTestingFirst.setName("testName1");
        hotelForTestingFirst.setDescription("testDescription1");
        hotelForTestingFirst.setAddress(addressForTestingFirst);
        hotelForTestingFirst.setArrivalTime(arrivalTimeForTestingFirst);
        hotelForTestingFirst.setContacts(contactsForTestingFirst);

        //second hotel
        addressForTestingSecond.setCity("testCity2");
        addressForTestingSecond.setCountry("testCountry2");
        addressForTestingSecond.setStreet("testStreet2");
        addressForTestingSecond.setPostCode("testPostCode2");
        addressForTestingSecond.setHouseNumber(2);

        arrivalTimeForTestingSecond.setCheckIn("testCheckIn2");
        arrivalTimeForTestingSecond.setCheckOut("testCheckOut2");

        contactsForTestingSecond.setEmail("testEmail2");
        contactsForTestingSecond.setPhone("testPhone2");

        hotelForTestingSecond = new Hotel();
        hotelForTestingSecond.setId(2L);
        hotelForTestingSecond.setBrand("testBrand2");
        hotelForTestingSecond.setName("testName2");
        hotelForTestingSecond.setDescription("testDescription2");
        hotelForTestingSecond.setAddress(addressForTestingSecond);
        hotelForTestingSecond.setArrivalTime(arrivalTimeForTestingSecond);
        hotelForTestingSecond.setContacts(contactsForTestingSecond);

        //fullHotelDto
        addressDtoForTesting = new AddressDto(
                1,
                "testStreet1",
                "testCity1",
                "testCountry1",
                "testPostCode1"
        );

        arrivalTimeDtoForTesting = new ArrivalTimeDto(
                "testCheckIn1",
                "testCheckOut1"
        );

        contactsDtoForTesting = new ContactsDto(
                "testPhone1",
                "testEmail1"
        );

        hotelFullDtoForTesting = new HotelFullDto(
                1L,
                "testName1",
                "testDescription1",
                "testBrand1",
                addressDtoForTesting,
                contactsDtoForTesting,
                arrivalTimeDtoForTesting,
                Set.of()
        );

        //first shortHotelDto
        hotelShortDtoForTestingFirst = new HotelShortDto(
                1L,
                "testName1",
                "testDescription1",
                "1 testStreet1, testCity1, testPostCode1, testCountry1",
                "testPhone1"
        );

        //second shortHotelDto
        hotelShortDtoForTestingSecond = new HotelShortDto(
                2L,
                "testName2",
                "testDescription2",
                "2 testStreet2, testCity2, testPostCode2, testCountry2",
                "testPhone2"
        );

        hotelShortDtoForTestingList = List.of(hotelShortDtoForTestingFirst, hotelShortDtoForTestingSecond);

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