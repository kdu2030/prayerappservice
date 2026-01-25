package com.kevin.prayerappservice.request.dtos;

public class SinglePrayerRequestGetQuery {
    public int prayerRequestId;
    public int userId;

    public SinglePrayerRequestGetQuery(int prayerRequestId, int userId) {
        this.prayerRequestId = prayerRequestId;
        this.userId = userId;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
