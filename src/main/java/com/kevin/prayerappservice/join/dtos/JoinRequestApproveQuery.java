package com.kevin.prayerappservice.join.dtos;

public class JoinRequestApproveQuery {
    private int targetPrayerGroupId;
    private int[] joinRequestIds;

    public JoinRequestApproveQuery(){}

    public JoinRequestApproveQuery(int targetPrayerGroupId, int[] joinRequestIds) {
        this.targetPrayerGroupId = targetPrayerGroupId;
        this.joinRequestIds = joinRequestIds;
    }

    public int getTargetPrayerGroupId() {
        return targetPrayerGroupId;
    }

    public void setTargetPrayerGroupId(int targetPrayerGroupId) {
        this.targetPrayerGroupId = targetPrayerGroupId;
    }

    public int[] getJoinRequestIds() {
        return joinRequestIds;
    }

    public void setJoinRequestIds(int[] joinRequestIds) {
        this.joinRequestIds = joinRequestIds;
    }
}
