package com.kevin.prayerappservice.group.models;

import java.util.List;

public class PrayerGroupSearchResponse {
    private List<PrayerGroupSummaryModel> prayerGroups;

    public PrayerGroupSearchResponse() {}

    public PrayerGroupSearchResponse(List<PrayerGroupSummaryModel> prayerGroups) {
        this.prayerGroups = prayerGroups;
    }

    public List<PrayerGroupSummaryModel> getPrayerGroups() {
        return prayerGroups;
    }

    public void setPrayerGroups(List<PrayerGroupSummaryModel> prayerGroups) {
        this.prayerGroups = prayerGroups;
    }
}
