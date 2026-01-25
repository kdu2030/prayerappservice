package com.kevin.prayerappservice.request.models;

import java.time.LocalDateTime;

public class PrayerRequestCommentModel {
    private int prayerRequestCommentId;
    private LocalDateTime submittedDate;
    private PrayerRequestUserSummary user;

    public PrayerRequestCommentModel(){}

    public PrayerRequestCommentModel(int prayerRequestCommentId, LocalDateTime submittedDate, PrayerRequestUserSummary user) {
        this.prayerRequestCommentId = prayerRequestCommentId;
        this.submittedDate = submittedDate;
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
}
