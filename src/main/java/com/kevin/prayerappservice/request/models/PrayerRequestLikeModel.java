package com.kevin.prayerappservice.request.models;

import java.time.LocalDateTime;

public class PrayerRequestLikeModel {
    private int prayerRequestLikeId;
    private PrayerRequestModel prayerRequest;
    private PrayerRequestUserSummary submittedUser;
    private LocalDateTime submittedDate;

    public PrayerRequestLikeModel(int prayerRequestLikeId, PrayerRequestModel prayerRequest, PrayerRequestUserSummary submittedUser, LocalDateTime submittedDate) {
        this.prayerRequestLikeId = prayerRequestLikeId;
        this.prayerRequest = prayerRequest;
        this.submittedUser = submittedUser;
        this.submittedDate = submittedDate;
    }

    public int getPrayerRequestLikeId() {
        return prayerRequestLikeId;
    }

    public void setPrayerRequestLikeId(int prayerRequestLikeId) {
        this.prayerRequestLikeId = prayerRequestLikeId;
    }

    public PrayerRequestModel getPrayerRequest() {
        return prayerRequest;
    }

    public void setPrayerRequest(PrayerRequestModel prayerRequest) {
        this.prayerRequest = prayerRequest;
    }

    public PrayerRequestUserSummary getSubmittedUser() {
        return submittedUser;
    }

    public void setSubmittedUser(PrayerRequestUserSummary submittedUser) {
        this.submittedUser = submittedUser;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
