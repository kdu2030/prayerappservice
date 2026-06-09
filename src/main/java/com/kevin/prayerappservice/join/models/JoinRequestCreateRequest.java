package com.kevin.prayerappservice.join.models;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class JoinRequestCreateRequest {
    @NotNull
    private int userId;

    @NotNull
    private OffsetDateTime submittedDate;

    public JoinRequestCreateRequest(){}

    public JoinRequestCreateRequest(int userId, OffsetDateTime submittedDate) {
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
