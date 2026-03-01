package com.kevin.prayerappservice.request.models;

import java.time.LocalDateTime;

public class PrayerRequestCommentCreateRequest {
    private int userId;
    private String comment;
    private LocalDateTime submittedDate;

    public PrayerRequestCommentCreateRequest(){}

    public PrayerRequestCommentCreateRequest(int userId, String comment, LocalDateTime submittedDate) {
        this.userId = userId;
        this.comment = comment;
        this.submittedDate = submittedDate;
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
