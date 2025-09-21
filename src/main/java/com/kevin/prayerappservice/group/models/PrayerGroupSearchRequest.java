package com.kevin.prayerappservice.group.models;

import jakarta.validation.constraints.NotNull;

public class PrayerGroupSearchRequest {
    @NotNull
    private String groupNameQuery;
    private Integer maxNumResults;

    public PrayerGroupSearchRequest(){}

    public PrayerGroupSearchRequest(String groupNameQuery, Integer maxNumResults) {
        this.groupNameQuery = groupNameQuery;
        this.maxNumResults = maxNumResults;
    }

    public String getGroupNameQuery() {
        return groupNameQuery;
    }

    public void setGroupNameQuery(String groupNameQuery) {
        this.groupNameQuery = groupNameQuery;
    }

    public Integer getMaxNumResults() {
        return maxNumResults;
    }

    public void setMaxNumResults(Integer maxNumResults) {
        this.maxNumResults = maxNumResults;
    }
}
