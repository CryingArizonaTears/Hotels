package com.gpsolutions.hotels.service;

import java.util.Map;

public interface HotelAnalyticsService {

    Map<String, Integer> histogramBy(String param);
}
