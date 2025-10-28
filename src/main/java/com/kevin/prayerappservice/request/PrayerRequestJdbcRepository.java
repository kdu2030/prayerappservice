package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateQuery;
import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateResult;
import com.kevin.prayerappservice.request.dtos.PrayerRequestGetQuery;
import com.kevin.prayerappservice.request.dtos.PrayerRequestGetResult;

import java.util.List;

public interface PrayerRequestJdbcRepository {
    PrayerRequestCreateResult createPrayerRequest(PrayerRequestCreateQuery createQuery);
    List<PrayerRequestGetResult> getPrayerRequests(PrayerRequestGetQuery getQuery);
}
