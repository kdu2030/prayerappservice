package com.kevin.prayerappservice.request.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PrayerRequestCreateRequest {
    private int userId;
    private int prayerGroupId;

    @NotBlank
    @Size(max = 255)
    private String requestTitle;

    @NotBlank
    private String requestDescription;

    private LocalDateTime expirationDate;

    private LocalDateTime createdDate;

    public PrayerRequestCreateRequest(){}

    public PrayerRequestCreateRequest(int userId, int prayerGroupId, String requestTitle, String requestDescription, LocalDateTime expirationDate, LocalDateTime createdDate) {
        this.userId = userId;
        this.prayerGroupId = prayerGroupId;
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;
        this.expirationDate = expirationDate;
        this.createdDate = createdDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(int prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
    }
}
