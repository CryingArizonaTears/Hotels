package com.gpsolutions.hotels.service;

import java.util.Map;

public interface HotelAnalyticsService {

    Map<String, Long> histogramBy(String param);
}
