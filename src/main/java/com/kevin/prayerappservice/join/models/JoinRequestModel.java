package com.kevin.prayerappservice.join.models;

import com.kevin.prayerappservice.user.models.UserSummary;

import java.time.OffsetDateTime;

public class JoinRequestModel {
    private int joinRequestId;
    private int prayerGroupId;
    private UserSummary user;
    private OffsetDateTime submittedDate;

    public JoinRequestModel(){}

    public JoinRequestModel(int joinRequestId, int prayerGroupId, UserSummary user, OffsetDateTime submittedDate) {
        this.joinRequestId = joinRequestId;
        this.prayerGroupId = prayerGroupId;
        this.user = user;
        this.submittedDate = submittedDate;
    }

    public int getJoinRequestId() {
        return joinRequestId;
    }

    public void setJoinRequestId(int joinRequestId) {
        this.joinRequestId = joinRequestId;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(int prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
    }

    public UserSummary getUser() {
        return user;
    }

    public void setUser(UserSummary user) {
        this.user = user;
    }

    public OffsetDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(OffsetDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
