package com.gpsolutions.hotels.utils;

import com.gpsolutions.hotels.dto.*;
import com.gpsolutions.hotels.entity.Address;
import com.gpsolutions.hotels.entity.ArrivalTime;
import com.gpsolutions.hotels.entity.Contacts;
import com.gpsolutions.hotels.entity.Hotel;

import java.util.List;
import java.util.Set;

public final class HotelTestData {

    private HotelTestData() {}

    public static Hotel createFirstHotel() {
        return createHotel(
                1L,
                "testName1",
                "testDescription1",
                "testBrand1",
                createFirstAddress(),
                createFirstContacts(),
                createFirstArrivalTime()
        );
    }

    public static Hotel createSecondHotel() {
        return createHotel(
                2L,
                "testName2",
                "testDescription2",
                "testBrand2",
                createSecondAddress(),
                createSecondContacts(),
                createSecondArrivalTime()
        );
    }

    private static Hotel createHotel(Long id,
                                     String name,
                                     String description,
                                     String brand,
                                     Address address,
                                     Contacts contacts,
                                     ArrivalTime arrivalTime) {
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setName(name);
        hotel.setDescription(description);
        hotel.setBrand(brand);
        hotel.setAddress(address);
        hotel.setContacts(contacts);
        hotel.setArrivalTime(arrivalTime);
        return hotel;
    }

    public static Address createFirstAddress() {
        Address address = new Address();
        address.setHouseNumber(1);
        address.setStreet("testStreet1");
        address.setCity("testCity1");
        address.setCountry("testCountry1");
        address.setPostCode("testPostCode1");
        return address;
    }

    public static Address createSecondAddress() {
        Address address = new Address();
        address.setHouseNumber(2);
        address.setStreet("testStreet2");
        address.setCity("testCity2");
        address.setCountry("testCountry2");
        address.setPostCode("testPostCode2");
        return address;
    }

    public static Contacts createFirstContacts() {
        Contacts contacts = new Contacts();
        contacts.setPhone("testPhone1");
        contacts.setEmail("testEmail1");
        return contacts;
    }

    public static Contacts createSecondContacts() {
        Contacts contacts = new Contacts();
        contacts.setPhone("testPhone2");
        contacts.setEmail("testEmail2");
        return contacts;
    }

    public static ArrivalTime createFirstArrivalTime() {
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn("testCheckIn1");
        arrivalTime.setCheckOut("testCheckOut1");
        return arrivalTime;
    }

    public static ArrivalTime createSecondArrivalTime() {
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn("testCheckIn2");
        arrivalTime.setCheckOut("testCheckOut2");
        return arrivalTime;
    }

    public static HotelFullDto createFirstFullDto() {
        return new HotelFullDto(
                1L,
                "testName1",
                "testDescription1",
                "testBrand1",
                createFirstAddressDto(),
                createFirstContactsDto(),
                createFirstArrivalTimeDto(),
                Set.of()
        );
    }

    public static HotelShortDto createFirstShortDto() {
        return new HotelShortDto(
                1L,
                "testName1",
                "testDescription1",
                "1 testStreet1, testCity1, testPostCode1, testCountry1",
                "testPhone1"
        );
    }

    public static HotelShortDto createSecondShortDto() {
        return new HotelShortDto(
                2L,
                "testName2",
                "testDescription2",
                "2 testStreet2, testCity2, testPostCode2, testCountry2",
                "testPhone2"
        );
    }

    public static List<HotelShortDto> createShortDtoList() {
        return List.of(createFirstShortDto(), createSecondShortDto());
    }

    public static AddressDto createFirstAddressDto() {
        return new AddressDto(
                1,
                "testStreet1",
                "testCity1",
                "testCountry1",
                "testPostCode1"
        );
    }

    public static ContactsDto createFirstContactsDto() {
        return new ContactsDto(
                "testPhone1",
                "testEmail1"
        );
    }

    public static ArrivalTimeDto createFirstArrivalTimeDto() {
        return new ArrivalTimeDto(
                "testCheckIn1",
                "testCheckOut1"
        );
    }
}

