package com.kevin.prayerappservice.join.dtos;

import java.time.OffsetDateTime;

public class JoinRequestApproveQuery {
    private int targetPrayerGroupId;
    private int[] joinRequestIds;
    private OffsetDateTime approveDate;

    public JoinRequestApproveQuery(){}

    public JoinRequestApproveQuery(int targetPrayerGroupId, int[] joinRequestIds, OffsetDateTime approveDate) {
        this.targetPrayerGroupId = targetPrayerGroupId;
        this.joinRequestIds = joinRequestIds;
        this.approveDate = approveDate;
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

    public OffsetDateTime getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(OffsetDateTime approveDate) {
        this.approveDate = approveDate;
    }
}
