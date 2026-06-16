package com.kevin.prayerappservice.request.dtos;

import java.time.OffsetDateTime;

public class PrayerRequestUpdateQuery {
    private int userId;
    private int prayerRequestId;
    private String requestTitle;
    private String requestDescription;
    private OffsetDateTime expirationDate;

    public PrayerRequestUpdateQuery(int userId, int prayerRequestId, String requestTitle, String requestDescription, OffsetDateTime expirationDate) {
        this.userId = userId;
        this.prayerRequestId = prayerRequestId;
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;
        this.expirationDate = expirationDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
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
}
