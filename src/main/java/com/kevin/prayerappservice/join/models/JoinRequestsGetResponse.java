package com.kevin.prayerappservice.join.models;

import java.util.List;

public class JoinRequestsGetResponse {
    private List<JoinRequestModel> joinRequests;

    public JoinRequestsGetResponse(){}

    public JoinRequestsGetResponse(List<JoinRequestModel> joinRequests) {
        this.joinRequests = joinRequests;
    }

    public List<JoinRequestModel> getJoinRequests() {
        return joinRequests;
    }

    public void setJoinRequests(List<JoinRequestModel> joinRequests) {
        this.joinRequests = joinRequests;
    }
}
