package com.kevin.prayerappservice.group.dtos;

public class PrayerGroupSearchQuery {
    private String groupNameQuery;
    private int maxNumResults;

    public PrayerGroupSearchQuery(){}

    public PrayerGroupSearchQuery(String groupNameQuery, int maxNumResults) {
        this.groupNameQuery = groupNameQuery;
        this.maxNumResults = maxNumResults;
    }

    public String getGroupNameQuery() {
        return groupNameQuery;
    }

    public void setGroupNameQuery(String groupNameQuery) {
        this.groupNameQuery = groupNameQuery;
    }

    public int getMaxNumResults() {
        return maxNumResults;
    }

    public void setMaxNumResults(int maxNumResults) {
        this.maxNumResults = maxNumResults;
    }
}
