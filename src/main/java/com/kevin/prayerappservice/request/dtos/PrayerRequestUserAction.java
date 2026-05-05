package com.kevin.prayerappservice.request.dtos;

import java.util.List;

public class PrayerRequestUserAction {
    private int prayerRequestId;
    private List<Integer> userCommentIds;
    private List<Integer> userPrayerSessionIds;

    public PrayerRequestUserAction(int prayerRequestId, List<Integer> userCommentIds, List<Integer> userPrayerSessionIds) {
        this.prayerRequestId = prayerRequestId;
        this.userCommentIds = userCommentIds;
        this.userPrayerSessionIds = userPrayerSessionIds;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public List<Integer> getUserCommentIds() {
        return userCommentIds;
    }

    public void setUserCommentIds(List<Integer> userCommentIds) {
        this.userCommentIds = userCommentIds;
    }

    public List<Integer> getUserPrayerSessionIds() {
        return userPrayerSessionIds;
    }

    public void setUserPrayerSessionIds(List<Integer> userPrayerSessionIds) {
        this.userPrayerSessionIds = userPrayerSessionIds;
    }
}
