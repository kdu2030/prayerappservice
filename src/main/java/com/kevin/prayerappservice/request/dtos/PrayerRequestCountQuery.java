package com.kevin.prayerappservice.request.dtos;

public class PrayerRequestCountQuery {
    private int[] prayerGroupIds;
    private int[] creatorUserIds;
    private Integer bookmarkedUserId;
    private boolean includeExpired;
    private int[] excludedCreatorUserIds;

    public PrayerRequestCountQuery(int[] prayerGroupIds, int[] creatorUserIds, Integer bookmarkedUserId, boolean includeExpired, int[] excludedCreatorUserIds) {
        this.prayerGroupIds = prayerGroupIds;
        this.creatorUserIds = creatorUserIds;
        this.bookmarkedUserId = bookmarkedUserId;
        this.includeExpired = includeExpired;
        this.excludedCreatorUserIds = excludedCreatorUserIds;
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

    public int[] getExcludedCreatorUserIds() {
        return excludedCreatorUserIds;
    }

    public void setExcludedCreatorUserIds(int[] excludedCreatorUserIds) {
        this.excludedCreatorUserIds = excludedCreatorUserIds;
    }
}
