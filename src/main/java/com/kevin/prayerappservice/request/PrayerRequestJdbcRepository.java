package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.dtos.*;

import java.util.List;

public interface PrayerRequestJdbcRepository {
    PrayerRequestCreateResult createPrayerRequest(PrayerRequestCreateQuery createQuery);
    List<PrayerRequestGetResult> getPrayerRequests(PrayerRequestGetQuery getQuery);
    PrayerRequestCountResult getPrayerRequestsCount(PrayerRequestCountQuery countQuery);
    PrayerRequestGetResult getPrayerRequest(int prayerRequestId, int userId);
}
