package com.kevin.prayerappservice.request.models;

import java.util.List;

public class PrayerRequestGetResponse {
    private List<PrayerRequestModel> prayerRequests;
    private int totalCount;
    private int numberOfPages;
    private int pageIndex;

    public PrayerRequestGetResponse(List<PrayerRequestModel> prayerRequests, int totalCount, int numberOfPages, int pageIndex) {
        this.prayerRequests = prayerRequests;
        this.totalCount = totalCount;
        this.numberOfPages = numberOfPages;
        this.pageIndex = pageIndex;
    }

    public List<PrayerRequestModel> getPrayerRequests() {
        return prayerRequests;
    }

    public void setPrayerRequests(List<PrayerRequestModel> prayerRequests) {
        this.prayerRequests = prayerRequests;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
