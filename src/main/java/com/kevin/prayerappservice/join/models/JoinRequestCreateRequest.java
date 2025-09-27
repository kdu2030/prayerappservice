package com.kevin.prayerappservice.join.models;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JoinRequestCreateRequest {
    @NotNull
    private int userId;

    @NotNull
    private LocalDateTime submittedDate;

    public JoinRequestCreateRequest(){}

    public JoinRequestCreateRequest(int userId, LocalDateTime submittedDate) {
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
