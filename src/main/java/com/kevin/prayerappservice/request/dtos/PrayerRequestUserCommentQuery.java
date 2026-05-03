package com.kevin.prayerappservice.request.dtos;

public class PrayerRequestUserCommentQuery {
    private int[] prayerRequestIds;
    private int userId;

    public PrayerRequestUserCommentQuery(int[] prayerRequestIds, int userId) {
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
