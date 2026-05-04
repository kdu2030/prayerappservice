package com.kevin.prayerappservice.request.dtos;

public class PrayerRequestUserSessionResult {
    private int[] prayerRequestIds;
    private int userId;

    public PrayerRequestUserSessionResult(int[] prayerRequestIds, int userId) {
        this.prayerRequestIds = prayerRequestIds;
        this.userId = userId;
    }

    public int[] getPrayerRequestIds() {
        return prayerRequestIds;
    }

    public void setPrayerRequestIds(int[] prayerRequestIds) {
        this.prayerRequestIds = prayerRequestIds;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
