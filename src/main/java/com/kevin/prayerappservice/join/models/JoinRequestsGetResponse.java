package com.kevin.prayerappservice.join.models;

public class JoinRequestsGetResponse {
    private JoinRequestModel joinRequests;

    public JoinRequestsGetResponse(){}

    public JoinRequestsGetResponse(JoinRequestModel joinRequests) {
        this.joinRequests = joinRequests;
    }

    public JoinRequestModel getJoinRequests() {
        return joinRequests;
    }

    public void setJoinRequests(JoinRequestModel joinRequests) {
        this.joinRequests = joinRequests;
    }
}
