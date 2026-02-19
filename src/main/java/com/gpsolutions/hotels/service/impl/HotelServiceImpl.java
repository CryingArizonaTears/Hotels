package com.gpsolutions.hotels.service.impl;

import com.gpsolutions.hotels.dto.AmenitiesDto;
import com.gpsolutions.hotels.dto.HotelFullDto;
import com.gpsolutions.hotels.dto.HotelShortDto;
import com.gpsolutions.hotels.entity.Address_;
import com.gpsolutions.hotels.entity.Hotel;
import com.gpsolutions.hotels.entity.Hotel_;
import com.gpsolutions.hotels.mapper.HotelMapper;
import com.gpsolutions.hotels.repo.HotelRepository;
import com.gpsolutions.hotels.service.HotelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelServiceImpl implements HotelService {

    HotelRepository hotelRepository;
    HotelMapper hotelMapper;

    @Override
    public List<HotelShortDto> getAll() {
        var result = hotelRepository.findAll();
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Hotels not found");
        }
        return result.stream().map(hotelMapper::toShortDto).toList();
    }

    @Override
    public HotelFullDto getById(Long id) {
        return hotelRepository.findById(id).map(hotelMapper::toFullDto).orElseThrow(() -> new EntityNotFoundException("Hotel with id " + id + " not found"));
    }

    @Override
    public List<HotelShortDto> search(String name, String brand, String city, String country, String amenities) {
        Specification<Hotel> spec = (root, query, cb) -> null;

        if (name != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get(Hotel_.name), name));
        }

        if (brand != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get(Hotel_.brand), brand));
        }

        if (city != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get(Hotel_.address).get(Address_.city), city));
        }

        if (country != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get(Hotel_.address).get(Address_.country), country));
        }

        if (amenities != null) {
            spec = spec.and((root, query, cb) ->
                    cb.isMember(amenities, root.get(Hotel_.amenities))
            );
        }

        var result = hotelRepository.findAll(spec);
        if (result.isEmpty()) {
            throw  new EntityNotFoundException("Hotels with given params not found");
        }

        return result.stream()
                .map(hotelMapper::toShortDto)
                .toList();
    }

    @Override
    @Transactional
    public HotelShortDto create(HotelFullDto hotelFullDto) {
        var hotel = hotelRepository.save(hotelMapper.fromFullDto(hotelFullDto));
        return hotelMapper.toShortDto(hotel);
    }

    @Override
    @Transactional
    public void addAmenities(Long hotelId, AmenitiesDto amenities) {
        var hotelFromRepo = hotelRepository.findById(hotelId).orElseThrow(() -> new EntityNotFoundException("Hotel with id " + hotelId + " not found"));
        hotelFromRepo.setAmenities(amenities.amenities());
        hotelRepository.save(hotelFromRepo);
    }
}
