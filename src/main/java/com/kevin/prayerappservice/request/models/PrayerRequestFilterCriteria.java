package com.kevin.prayerappservice.request.models;

import com.kevin.prayerappservice.common.SortConfig;
import com.kevin.prayerappservice.common.SortDirection;
import com.kevin.prayerappservice.request.constants.PrayerRequestSortField;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PrayerRequestFilterCriteria {
    private List<Integer> prayerGroupIds;
    @NotNull
    private Integer pageIndex;

    @NotNull
    private Integer pageSize;

    private SortConfig<PrayerRequestSortField> sortConfig = new SortConfig<>(PrayerRequestSortField.CREATED_DATE, SortDirection.DESCENDING);

    private boolean includeExpiredPrayerRequests = false;
    private List<Integer> excludedCreatorUserIds;
    private Integer bookmarkedUserId;
    private List<Integer> creatorUserIds;

    public PrayerRequestFilterCriteria(){}

    public PrayerRequestFilterCriteria(List<Integer> prayerGroupIds, Integer pageIndex, Integer pageSize, SortConfig<PrayerRequestSortField> sortConfig, boolean includeExpiredPrayerRequests, List<Integer> excludedCreatorUserIds, Integer bookmarkedUserId, List<Integer> creatorUserIds) {
        this.prayerGroupIds = prayerGroupIds;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.includeExpiredPrayerRequests = includeExpiredPrayerRequests;
        this.excludedCreatorUserIds = excludedCreatorUserIds;
        this.bookmarkedUserId = bookmarkedUserId;
        this.creatorUserIds = creatorUserIds;

        if(sortConfig != null){
            this.sortConfig = sortConfig;
        }
    }

    public List<Integer> getPrayerGroupIds() {
        return prayerGroupIds;
    }

    public void setPrayerGroupIds(List<Integer> prayerGroupIds) {
        this.prayerGroupIds = prayerGroupIds;
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

    public List<Integer> getExcludedCreatorUserIds() {
        return excludedCreatorUserIds;
    }

    public void setExcludedCreatorUserIds(List<Integer> excludedCreatorUserIds) {
        this.excludedCreatorUserIds = excludedCreatorUserIds;
    }

    public Integer getBookmarkedUserId() {
        return bookmarkedUserId;
    }

    public void setBookmarkedUserId(Integer bookmarkedUserId) {
        this.bookmarkedUserId = bookmarkedUserId;
    }

    public List<Integer> getCreatorUserIds() {
        return creatorUserIds;
    }

    public void setCreatorUserIds(List<Integer> creatorUserIds) {
        this.creatorUserIds = creatorUserIds;
    }
}
