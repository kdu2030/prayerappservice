package com.kevin.prayerappservice.request.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public class PrayerRequestCreateRequest {
    private int userId;
    private int prayerGroupId;

    @NotBlank
    @Size(max = 255)
    private String requestTitle;

    @NotBlank
    private String requestDescription;

    private OffsetDateTime expirationDate;

    private OffsetDateTime createdDate;

    public PrayerRequestCreateRequest(){}

    public PrayerRequestCreateRequest(int userId, int prayerGroupId, String requestTitle, String requestDescription, OffsetDateTime expirationDate, OffsetDateTime createdDate) {
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

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(OffsetDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(int prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
    }
}
