package com.kevin.prayerappservice.request.models;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class PrayerRequestUpdateRequest {
    @NotNull
    private String requestTitle;
    @NotNull
    private String requestDescription;

    private OffsetDateTime expirationDate;

    public PrayerRequestUpdateRequest(){}

    public PrayerRequestUpdateRequest(String requestTitle, String requestDescription, OffsetDateTime expirationDate) {
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;
        this.expirationDate = expirationDate;
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
