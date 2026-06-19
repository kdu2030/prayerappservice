package com.kevin.prayerappservice.group.models;

import java.time.OffsetDateTime;

public class PrayerGroupUserCreateRequest {
    private OffsetDateTime joinDate;

    public PrayerGroupUserCreateRequest(){}

    public PrayerGroupUserCreateRequest(OffsetDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public OffsetDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(OffsetDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
