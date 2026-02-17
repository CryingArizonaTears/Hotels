package com.gpsolutions.hotels.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ArrivalTime {
    String checkIn;
    String checkOut;
}
