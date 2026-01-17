package com.kevin.prayerappservice.request.models;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PrayerRequestActionCreateRequest {
    @NotNull
    private int userId;
    private LocalDateTime submittedDate;

    public PrayerRequestActionCreateRequest(){}

    public PrayerRequestActionCreateRequest(int userId, LocalDateTime submittedDate) {
        this.userId = userId;
        this.submittedDate = submittedDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
