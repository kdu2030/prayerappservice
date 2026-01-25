package com.kevin.prayerappservice.request.dtos;

public class PrayerRequestCommentQuery {
    private int prayerRequestId;

    public PrayerRequestCommentQuery(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }
}
