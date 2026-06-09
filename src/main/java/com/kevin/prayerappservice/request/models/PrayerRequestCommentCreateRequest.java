package com.kevin.prayerappservice.request.models;

import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;

public class PrayerRequestCommentCreateRequest {
    private int userId;

    @NotBlank
    private String comment;
    private OffsetDateTime submittedDate;

    public PrayerRequestCommentCreateRequest(){}

    public PrayerRequestCommentCreateRequest(int userId, String comment, OffsetDateTime submittedDate) {
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

    public OffsetDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(OffsetDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
