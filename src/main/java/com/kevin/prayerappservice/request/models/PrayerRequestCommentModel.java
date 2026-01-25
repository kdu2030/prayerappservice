package com.kevin.prayerappservice.request.models;

import java.time.LocalDateTime;

public class PrayerRequestCommentModel {
    private int prayerRequestCommentId;
    private LocalDateTime submittedDate;
    private String comment;
    private PrayerRequestUserSummary user;

    public PrayerRequestCommentModel(){}

    public PrayerRequestCommentModel(int prayerRequestCommentId, LocalDateTime submittedDate, String comment, PrayerRequestUserSummary user) {
        this.prayerRequestCommentId = prayerRequestCommentId;
        this.submittedDate = submittedDate;
        this.comment = comment;
        this.user = user;
    }

    public int getPrayerRequestCommentId() {
        return prayerRequestCommentId;
    }

    public void setPrayerRequestCommentId(int prayerRequestCommentId) {
        this.prayerRequestCommentId = prayerRequestCommentId;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }

    public PrayerRequestUserSummary getUser() {
        return user;
    }

    public void setUser(PrayerRequestUserSummary user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
