package com.kevin.prayerappservice.request.dtos;

import java.util.List;

public class PrayerRequestGetQuery {
    private int targetUserId;
    private int[] prayerGroupIds;
    private int[] creatorUserIds;
    private Integer bookmarkedUserId;
    private boolean includeExpired;
    private String sortField;
    private String sortDirection;
    private int skip;
    private int take;

    public PrayerRequestGetQuery(){}

    public PrayerRequestGetQuery(int targetUserId, int[] prayerGroupIds, int[] creatorUserIds, Integer bookmarkedUserId, boolean includeExpired, String sortField, String sortDirection, int skip, int take) {
        this.targetUserId = targetUserId;
        this.prayerGroupIds = prayerGroupIds;
        this.creatorUserIds = creatorUserIds;
        this.bookmarkedUserId = bookmarkedUserId;
        this.includeExpired = includeExpired;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.skip = skip;
        this.take = take;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
    }

    public int[] getPrayerGroupIds() {
        return prayerGroupIds;
    }

    public void setPrayerGroupIds(int[] prayerGroupIds) {
        this.prayerGroupIds = prayerGroupIds;
    }

    public int[] getCreatorUserIds() {
        return creatorUserIds;
    }

    public void setCreatorUserIds(int[] creatorUserIds) {
        this.creatorUserIds = creatorUserIds;
    }

    public Integer getBookmarkedUserId() {
        return bookmarkedUserId;
    }

    public void setBookmarkedUserId(Integer bookmarkedUserId) {
        this.bookmarkedUserId = bookmarkedUserId;
    }

    public boolean isIncludeExpired() {
        return includeExpired;
    }

    public void setIncludeExpired(boolean includeExpired) {
        this.includeExpired = includeExpired;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }
}
