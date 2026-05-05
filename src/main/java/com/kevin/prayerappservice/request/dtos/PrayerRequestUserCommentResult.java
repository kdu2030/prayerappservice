package com.kevin.prayerappservice.request.dtos;

public class PrayerRequestUserCommentResult {
    private int prayerRequestId;
    private int prayerRequestCommentId;

    public PrayerRequestUserCommentResult(){}

    public PrayerRequestUserCommentResult(int prayerRequestId, int prayerRequestCommentId) {
        this.prayerRequestId = prayerRequestId;
        this.prayerRequestCommentId = prayerRequestCommentId;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public int getPrayerRequestCommentId() {
        return prayerRequestCommentId;
    }

    public void setPrayerRequestCommentId(int prayerRequestCommentId) {
        this.prayerRequestCommentId = prayerRequestCommentId;
    }
}
