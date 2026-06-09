package com.kevin.prayerappservice.request.models;

import java.time.OffsetDateTime;

public class PrayerRequestLikeModel {
    private int prayerRequestLikeId;
    private int prayerRequestId;
    private int submittedUserId;
    private OffsetDateTime submittedDate;

    public PrayerRequestLikeModel(){}

    public PrayerRequestLikeModel(int prayerRequestLikeId, int prayerRequestId, int submittedUserId, OffsetDateTime submittedDate) {
        this.prayerRequestLikeId = prayerRequestLikeId;
        this.prayerRequestId = prayerRequestId;
        this.submittedUserId = submittedUserId;
        this.submittedDate = submittedDate;
    }

    public int getPrayerRequestLikeId() {
        return prayerRequestLikeId;
    }

    public void setPrayerRequestLikeId(int prayerRequestLikeId) {
        this.prayerRequestLikeId = prayerRequestLikeId;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public int getSubmittedUserId() {
        return submittedUserId;
    }

    public void setSubmittedUserId(int submittedUserId) {
        this.submittedUserId = submittedUserId;
    }

    public OffsetDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(OffsetDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
