package com.kevin.prayerappservice.request.dtos;

public class PrayerRequestUserSessionResult {
    private int prayerRequestId;
    private int prayerSessionId;

    public PrayerRequestUserSessionResult(){}

    public PrayerRequestUserSessionResult(int prayerRequestId, int prayerSessionId) {
        this.prayerRequestId = prayerRequestId;
        this.prayerSessionId = prayerSessionId;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public int getPrayerSessionId() {
        return prayerSessionId;
    }

    public void setPrayerSessionId(int prayerSessionId) {
        this.prayerSessionId = prayerSessionId;
    }
}
