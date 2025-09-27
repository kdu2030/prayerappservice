package com.kevin.prayerappservice.join.dtos;

public class JoinRequestDeleteQuery {
    private int prayerGroupId;
    private int[] joinRequestIds;

    public JoinRequestDeleteQuery(){}

    public JoinRequestDeleteQuery(int prayerGroupId, int[] joinRequestIds) {
        this.prayerGroupId = prayerGroupId;
        this.joinRequestIds = joinRequestIds;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(int prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
    }

    public int[] getJoinRequestIds() {
        return joinRequestIds;
    }

    public void setJoinRequestIds(int[] joinRequestIds) {
        this.joinRequestIds = joinRequestIds;
    }
}
