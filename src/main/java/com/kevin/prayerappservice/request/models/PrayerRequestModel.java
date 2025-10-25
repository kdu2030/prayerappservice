package com.kevin.prayerappservice.request.models;

import java.time.LocalDateTime;

public class PrayerRequestModel {
    private int prayerRequestId;
    private String requestTitle;
    private String requestDescription;
    private LocalDateTime createdDate;
    private LocalDateTime expirationDate;
    private PrayerRequestPrayerGroupSummary prayerGroup;
    private PrayerRequestUserSummary user;
    private int likeCount;
    private int commentCount;
    private int prayedCount;
    private boolean isUserPrayed;
    private boolean isUserCommented;
    private boolean isUserLiked;

    public PrayerRequestModel(){}

    public PrayerRequestModel(int prayerRequestId, String requestTitle, String requestDescription, LocalDateTime createdDate, LocalDateTime expirationDate, PrayerRequestPrayerGroupSummary prayerGroup, PrayerRequestUserSummary user, int likeCount, int commentCount, int prayedCount, boolean isUserPrayed, boolean isUserCommented, boolean isUserLiked) {
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
        this.isUserPrayed = isUserPrayed;
        this.isUserCommented = isUserCommented;
        this.isUserLiked = isUserLiked;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
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

    public boolean isUserPrayed() {
        return isUserPrayed;
    }

    public void setUserPrayed(boolean userPrayed) {
        isUserPrayed = userPrayed;
    }

    public boolean isUserCommented() {
        return isUserCommented;
    }

    public void setUserCommented(boolean userCommented) {
        isUserCommented = userCommented;
    }

    public boolean isUserLiked() {
        return isUserLiked;
    }

    public void setUserLiked(boolean userLiked) {
        isUserLiked = userLiked;
    }
}
