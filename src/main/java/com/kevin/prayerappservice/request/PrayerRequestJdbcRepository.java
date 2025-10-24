package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateQuery;
import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateResult;

public interface PrayerRequestJdbcRepository {
    PrayerRequestCreateResult createPrayerRequest(PrayerRequestCreateQuery createQuery);
}
