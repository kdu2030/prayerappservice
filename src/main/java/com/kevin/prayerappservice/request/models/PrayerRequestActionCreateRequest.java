package com.kevin.prayerappservice.request.models;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class PrayerRequestActionCreateRequest {
    @NotNull
    private int userId;
    private OffsetDateTime submittedDate;

    public PrayerRequestActionCreateRequest(){}

    public PrayerRequestActionCreateRequest(int userId, OffsetDateTime submittedDate) {
        this.userId = userId;
        this.submittedDate = submittedDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public OffsetDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(OffsetDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
