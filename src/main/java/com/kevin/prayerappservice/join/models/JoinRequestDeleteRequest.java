package com.kevin.prayerappservice.join.models;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class JoinRequestDeleteRequest {
    @NotNull
    private List<Integer> joinRequestIds;

    public JoinRequestDeleteRequest(){}

    public JoinRequestDeleteRequest(List<Integer> joinRequestIds) {
        this.joinRequestIds = joinRequestIds;
    }

    public List<Integer> getJoinRequestIds() {
        return joinRequestIds;
    }

    public void setJoinRequestIds(List<Integer> joinRequestIds) {
        this.joinRequestIds = joinRequestIds;
    }
}
