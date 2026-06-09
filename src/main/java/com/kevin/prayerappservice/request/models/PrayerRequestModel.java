package com.kevin.prayerappservice.request.models;

import java.time.OffsetDateTime;
import java.util.List;

public class PrayerRequestModel {
    private int prayerRequestId;
    private String requestTitle;
    private String requestDescription;
    private OffsetDateTime createdDate;
    private OffsetDateTime expirationDate;
    private PrayerRequestPrayerGroupSummary prayerGroup;
    private PrayerRequestUserSummary user;
    private int likeCount;
    private int commentCount;
    private int prayedCount;
    private Integer userLikeId;
    private List<Integer> userCommentIds;
    private Integer userBookmarkId;
    private List<Integer> userPrayerSessionIds;

    public PrayerRequestModel(){}

    public PrayerRequestModel(int prayerRequestId, String requestTitle, String requestDescription, OffsetDateTime createdDate, OffsetDateTime expirationDate, PrayerRequestPrayerGroupSummary prayerGroup, PrayerRequestUserSummary user, int likeCount, int commentCount, int prayedCount, Integer userLikeId, List<Integer> userCommentIds, Integer userBookmarkId, List<Integer> userPrayerSessionIds) {
        this.prayerRequestId = prayerRequestId;
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.prayerGroup = prayerGroup;
        this.user = user;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.prayedCount = prayedCount;
        this.userLikeId = userLikeId;
        this.userCommentIds = userCommentIds;
        this.userBookmarkId = userBookmarkId;
        this.userPrayerSessionIds = userPrayerSessionIds;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(OffsetDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public PrayerRequestPrayerGroupSummary getPrayerGroup() {
        return prayerGroup;
    }

    public void setPrayerGroup(PrayerRequestPrayerGroupSummary prayerGroup) {
        this.prayerGroup = prayerGroup;
    }

    public PrayerRequestUserSummary getUser() {
        return user;
    }

    public void setUser(PrayerRequestUserSummary user) {
        this.user = user;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getPrayedCount() {
        return prayedCount;
    }

    public void setPrayedCount(int prayedCount) {
        this.prayedCount = prayedCount;
    }

    public Integer getUserLikeId() {
        return userLikeId;
    }

    public void setUserLikeId(Integer userLikeId) {
        this.userLikeId = userLikeId;
    }

    public List<Integer> getUserCommentIds() {
        return userCommentIds;
    }

    public void setUserCommentIds(List<Integer> userCommentIds){
        this.userCommentIds = userCommentIds;
    }

    public Integer getUserBookmarkId() {
        return userBookmarkId;
    }

    public void setUserBookmarkId(Integer userBookmarkId) {
        this.userBookmarkId = userBookmarkId;
    }

    public List<Integer> getUserPrayerSessionIds() {
        return userPrayerSessionIds;
    }

    public void setUserPrayerSessionIds(List<Integer> userPrayerSessionIds) {
        this.userPrayerSessionIds = userPrayerSessionIds;
    }
}
