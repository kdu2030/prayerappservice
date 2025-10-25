package com.kevin.prayerappservice.request.models;

import com.kevin.prayerappservice.common.SortConfig;
import com.kevin.prayerappservice.request.constants.PrayerRequestSortField;

import java.util.List;

public class PrayerRequestFilterCriteria {
    private List<Integer> prayerGroupIds;
    private List<Integer> creatorUserIds;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer bookmarkedByUserId;
    private SortConfig<PrayerRequestSortField> sortConfig;
    private boolean includeExpiredPrayerRequests = false;

    public PrayerRequestFilterCriteria(){}

    public PrayerRequestFilterCriteria(List<Integer> prayerGroupIds, List<Integer> creatorUserIds, Integer pageIndex, Integer pageSize, Integer bookmarkedByUserId, SortConfig<PrayerRequestSortField> sortConfig, boolean includeExpiredPrayerRequests) {
        this.prayerGroupIds = prayerGroupIds;
        this.creatorUserIds = creatorUserIds;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.bookmarkedByUserId = bookmarkedByUserId;
        this.sortConfig = sortConfig;
        this.includeExpiredPrayerRequests = includeExpiredPrayerRequests;
    }

    public List<Integer> getPrayerGroupIds() {
        return prayerGroupIds;
    }

    public void setPrayerGroupIds(List<Integer> prayerGroupIds) {
        this.prayerGroupIds = prayerGroupIds;
    }

    public List<Integer> getCreatorUserIds() {
        return creatorUserIds;
    }

    public void setCreatorUserIds(List<Integer> creatorUserIds) {
        this.creatorUserIds = creatorUserIds;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getBookmarkedByUserId() {
        return bookmarkedByUserId;
    }

    public void setBookmarkedByUserId(Integer bookmarkedByUserId) {
        this.bookmarkedByUserId = bookmarkedByUserId;
    }

    public SortConfig<PrayerRequestSortField> getSortConfig() {
        return sortConfig;
    }

    public void setSortConfig(SortConfig<PrayerRequestSortField> sortConfig) {
        this.sortConfig = sortConfig;
    }

    public boolean isIncludeExpiredPrayerRequests() {
        return includeExpiredPrayerRequests;
    }

    public void setIncludeExpiredPrayerRequests(boolean includeExpiredPrayerRequests) {
        this.includeExpiredPrayerRequests = includeExpiredPrayerRequests;
    }
}
