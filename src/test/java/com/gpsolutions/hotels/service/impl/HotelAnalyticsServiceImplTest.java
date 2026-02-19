package com.gpsolutions.hotels.service.impl;

import com.gpsolutions.hotels.repo.HotelRepository;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(makeFinal = false)
class HotelAnalyticsServiceImplTest {

    @Mock
    HotelRepository hotelRepository;
    @InjectMocks
    HotelAnalyticsServiceImpl hotelAnalyticsService;

    @Test
    void histogramBy_Success() {
        when(hotelRepository.countByBrand()).thenReturn(List.of(new Object[]{"TestBrand1", 3L}, new Object[]{"TestBrand2", 2L}));
        var result = hotelAnalyticsService.histogramBy("Brand");
        assertThat(result).containsEntry("TestBrand1", 3L).containsEntry("TestBrand2", 2L);
        verify(hotelRepository).countByBrand();
    }

    @Test
    void histogramBy_UnknownParam_ShouldThrow() {
        assertThatThrownBy(() -> hotelAnalyticsService.histogramBy("wrong"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}