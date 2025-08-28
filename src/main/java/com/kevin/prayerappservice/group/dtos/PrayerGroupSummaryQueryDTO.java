package com.kevin.prayerappservice.group.dtos;

public class PrayerGroupSummaryQueryDTO {
    private int targetUserId;

    public PrayerGroupSummaryQueryDTO(int targetUserId) {
        this.targetUserId = targetUserId;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
    }
}
