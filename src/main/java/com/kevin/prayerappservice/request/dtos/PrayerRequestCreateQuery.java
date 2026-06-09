package com.kevin.prayerappservice.request.dtos;

import java.time.OffsetDateTime;

public class PrayerRequestCreateQuery {
    private String requestTitle;
    private String requestDescription;
    private OffsetDateTime createdDate;
    private OffsetDateTime expirationDate;
    private int prayerGroupId;
    private int userId;

    public PrayerRequestCreateQuery(String requestTitle, String requestDescription, OffsetDateTime createdDate, OffsetDateTime expirationDate, int prayerGroupId, int userId) {
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.prayerGroupId = prayerGroupId;
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

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(OffsetDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(int prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
