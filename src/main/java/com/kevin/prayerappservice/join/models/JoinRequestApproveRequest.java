package com.kevin.prayerappservice.join.models;

import java.util.List;

public class JoinRequestApproveRequest {
    private List<Integer> joinRequestIds;

    public JoinRequestApproveRequest(){}

    public JoinRequestApproveRequest(List<Integer> joinRequestIds) {
        this.joinRequestIds = joinRequestIds;
    }

    public List<Integer> getJoinRequestIds() {
        return joinRequestIds;
    }

    public void setJoinRequestIds(List<Integer> joinRequestIds) {
        this.joinRequestIds = joinRequestIds;
    }
}
