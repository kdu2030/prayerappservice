package com.kevin.prayerappservice.request.dtos;

public class PrayerRequestCountResult {
    private int prayerRequestCount;

    public PrayerRequestCountResult(){}

    public PrayerRequestCountResult(int prayerRequestCount) {
        this.prayerRequestCount = prayerRequestCount;
    }

    public int getPrayerRequestCount() {
        return prayerRequestCount;
    }

    public void setPrayerRequestCount(int prayerRequestCount) {
        this.prayerRequestCount = prayerRequestCount;
    }
}
