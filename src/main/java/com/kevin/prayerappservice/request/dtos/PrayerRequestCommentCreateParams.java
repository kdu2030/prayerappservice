package com.kevin.prayerappservice.request.dtos;

import java.time.LocalDateTime;

public class PrayerRequestCommentCreateParams {
    private int prayerRequestId;
    private int userId;
    private String comment;
    private LocalDateTime submittedDate;

    public PrayerRequestCommentCreateParams(int prayerRequestId, int userId, String comment, LocalDateTime submittedDate) {
        this.prayerRequestId = prayerRequestId;
        this.userId = userId;
        this.comment = comment;
        this.submittedDate = submittedDate;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
