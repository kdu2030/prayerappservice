package com.kevin.prayerappservice.join.models;

import com.kevin.prayerappservice.common.SortConfig;
import com.kevin.prayerappservice.join.constants.JoinRequestSortField;

public class JoinRequestsGetRequest {
    private SortConfig<JoinRequestSortField> sortConfig;

    public JoinRequestsGetRequest(){}

    public JoinRequestsGetRequest(SortConfig<JoinRequestSortField> sortConfig) {
        this.sortConfig = sortConfig;
    }

    public SortConfig<JoinRequestSortField> getSortConfig() {
        return sortConfig;
    }

    public void setSortConfig(SortConfig<JoinRequestSortField> sortConfig) {
        this.sortConfig = sortConfig;
    }
}
