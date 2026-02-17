package com.gpsolutions.hotels.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Contacts {
    String phone;
    String email;
}
