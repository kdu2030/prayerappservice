package com.kevin.prayerappservice.request.dtos;

public class PrayerRequestDeleteQuery {
    private int prayerRequestId;

    public PrayerRequestDeleteQuery(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }
}
