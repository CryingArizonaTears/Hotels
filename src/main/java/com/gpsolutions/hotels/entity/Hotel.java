package com.gpsolutions.hotels.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(makeFinal = false)
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    String brand;
    @Embedded
    Address address;
    @Embedded
    Contacts contacts;
    @Embedded
    ArrivalTime arrivalTime;
    @ElementCollection
    @CollectionTable(
            name = "hotel_amenities",
            joinColumns = @JoinColumn(name = "hotel_id")
    )
    @Column(name = "amenity")
    @OrderColumn(name = "amenity_order")
    List<String> amenities = new ArrayList<>();
}
