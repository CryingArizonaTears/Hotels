package com.gpsolutions.hotels.repo;

import com.gpsolutions.hotels.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @Query(value = "SELECT brand, COUNT(*) FROM hotel GROUP BY brand", nativeQuery = true)
    List<Object[]> countByBrand();

    @Query(value = "SELECT city, COUNT(*) FROM hotel GROUP BY city", nativeQuery = true)
    List<Object[]> countByCity();

    @Query(value = "SELECT country, COUNT(*) FROM hotel GROUP BY country", nativeQuery = true)
    List<Object[]> countByCountry();

    @Query(value = "SELECT amenity, COUNT(hotel_id) FROM hotel_amenities GROUP BY amenity", nativeQuery = true)
    List<Object[]> countByAmenities();

}
