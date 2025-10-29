package com.kevin.prayerappservice.request.models;

import java.util.List;

public class PrayerRequestGetResponse {
    private List<PrayerRequestModel> prayerRequests;

    public PrayerRequestGetResponse(List<PrayerRequestModel> prayerRequests) {
        this.prayerRequests = prayerRequests;
    }

    public List<PrayerRequestModel> getPrayerRequests() {
        return prayerRequests;
    }

    public void setPrayerRequests(List<PrayerRequestModel> prayerRequests) {
        this.prayerRequests = prayerRequests;
    }
}
