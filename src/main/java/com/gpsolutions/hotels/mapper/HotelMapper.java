package com.gpsolutions.hotels.mapper;

import com.gpsolutions.hotels.dto.*;
import com.gpsolutions.hotels.entity.Address;
import com.gpsolutions.hotels.entity.ArrivalTime;
import com.gpsolutions.hotels.entity.Contacts;
import com.gpsolutions.hotels.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.StringJoiner;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelShortDto toShortDto(Hotel hotel);

    default String map(Address address) {
        if (address == null) {
            return null;
        }

        return address.getHouseNumber() + " "
                + address.getStreet() + ", "
                + address.getCity() + ", "
                + address.getPostCode() + ", "
                + address.getCountry();
    }

    @Mapping(target = "address", source = "address")
    @Mapping(target = "contacts", source = "contacts")
    @Mapping(target = "arrivalTime", source = "arrivalTime")
    HotelFullDto toFullDto(Hotel hotel);

    default String formatAddress(Hotel hotel) {
        return new StringJoiner(", ")
                .add(hotel.getAddress().getHouseNumber() + "")
                .add(hotel.getAddress().getStreet())
                .add(hotel.getAddress().getCity())
                .add(hotel.getAddress().getCountry())
                .add(hotel.getAddress().getPostCode())
                .toString();
    }

    default Hotel fromFullDto(HotelFullDto dto) {
        Hotel hotel = new Hotel();
        hotel.setName(dto.name());
        hotel.setDescription(dto.description());
        hotel.setBrand(dto.brand());
        hotel.setAddress(mapAddress(dto.address()));
        hotel.setContacts(mapContacts(dto.contacts()));
        hotel.setArrivalTime(mapArrivalTime(dto.arrivalTime()));

        return hotel;
    }

    default Address mapAddress(AddressDto dto) {
        Address address = new Address();
        address.setHouseNumber(dto.houseNumber());
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setCountry(dto.country());
        address.setPostCode(dto.postCode());
        return address;
    }

    default Contacts mapContacts(ContactsDto dto) {
        Contacts contacts = new Contacts();
        contacts.setPhone(dto.phone());
        contacts.setEmail(dto.email());
        return contacts;
    }

    default ArrivalTime mapArrivalTime(ArrivalTimeDto dto) {
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(dto.checkIn());
        arrivalTime.setCheckOut(dto.checkOut());
        return arrivalTime;
    }
}
