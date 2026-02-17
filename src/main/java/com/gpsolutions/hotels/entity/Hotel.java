package com.gpsolutions.hotels.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_seq")
    @SequenceGenerator(name = "hotel_seq", sequenceName = "hotel_seq", allocationSize = 1)
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
    Set<String> amenities = new HashSet<>();
}
