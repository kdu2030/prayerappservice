package com.kevin.prayerappservice.join.models;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class JoinRequestCreateRequest {
    @NotNull
    private int userId;

    @NotNull
    private LocalDate submittedDate;

    public JoinRequestCreateRequest(){}

    public JoinRequestCreateRequest(int userId, LocalDate submittedDate) {
        this.userId = userId;
        this.submittedDate = submittedDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDate submittedDate) {
        this.submittedDate = submittedDate;
    }
}
