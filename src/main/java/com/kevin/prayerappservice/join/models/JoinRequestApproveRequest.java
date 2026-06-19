package com.kevin.prayerappservice.join.models;

import java.time.OffsetDateTime;
import java.util.List;

public class JoinRequestApproveRequest {
    private List<Integer> joinRequestIds;
    private OffsetDateTime approveDate;

    public JoinRequestApproveRequest(){}

    public JoinRequestApproveRequest(List<Integer> joinRequestIds, OffsetDateTime approveDate) {
        this.joinRequestIds = joinRequestIds;
        this.approveDate = approveDate;
    }

    public List<Integer> getJoinRequestIds() {
        return joinRequestIds;
    }

    public void setJoinRequestIds(List<Integer> joinRequestIds) {
        this.joinRequestIds = joinRequestIds;
    }

    public OffsetDateTime getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(OffsetDateTime approveDate) {
        this.approveDate = approveDate;
    }
}
