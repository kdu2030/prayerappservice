package com.kevin.prayerappservice.request.models;

import java.time.OffsetDateTime;

public class PrayerRequestBookmarkModel {
    private int prayerRequestBookmarkId;
    private int prayerRequestId;
    private int submittedUserId;
    private OffsetDateTime submittedDate;

    public PrayerRequestBookmarkModel(){}

    public PrayerRequestBookmarkModel(int prayerRequestBookmarkId, int prayerRequestId, int submittedUserId, OffsetDateTime submittedDate) {
        this.prayerRequestBookmarkId = prayerRequestBookmarkId;
        this.prayerRequestId = prayerRequestId;
        this.submittedUserId = submittedUserId;
        this.submittedDate = submittedDate;
    }

    public int getPrayerRequestBookmarkId() {
        return prayerRequestBookmarkId;
    }

    public void setPrayerRequestBookmarkId(int prayerRequestBookmarkId) {
        this.prayerRequestBookmarkId = prayerRequestBookmarkId;
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
