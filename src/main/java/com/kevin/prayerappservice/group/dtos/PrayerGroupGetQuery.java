package com.kevin.prayerappservice.group.dtos;

public class PrayerGroupGetQuery {
    private int targetPrayerGroupId;
    private int targetUserId;

    public PrayerGroupGetQuery(int targetPrayerGroupId, int targetUserId) {
        this.targetPrayerGroupId = targetPrayerGroupId;
        this.targetUserId = targetUserId;
    }

    public int getTargetPrayerGroupId() {
        return targetPrayerGroupId;
    }

    public void setTargetPrayerGroupId(int targetPrayerGroupId) {
        this.targetPrayerGroupId = targetPrayerGroupId;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
    }
}
