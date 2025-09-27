package com.kevin.prayerappservice.join.dtos;

public class JoinRequestQuery {
    private int targetPrayerGroupId;

    public JoinRequestQuery(){}

    public JoinRequestQuery(int targetPrayerGroupId) {
        this.targetPrayerGroupId = targetPrayerGroupId;
    }

    public int getTargetPrayerGroupId() {
        return targetPrayerGroupId;
    }

    public void setTargetPrayerGroupId(int targetPrayerGroupId) {
        this.targetPrayerGroupId = targetPrayerGroupId;
    }
}
