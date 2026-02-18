package com.gpsolutions.hotels.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Embeddable
@Data
@NoArgsConstructor
@FieldDefaults(makeFinal = false)
public class Contacts {
    String phone;
    String email;
}
