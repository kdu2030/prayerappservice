package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.common.SortConfig;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;

import java.util.List;

public class PrayerGroupUsersGetRequest {
    private List<PrayerGroupRole> prayerGroupRoles;
    private SortConfig sortConfig;

    public PrayerGroupUsersGetRequest(){}

    public PrayerGroupUsersGetRequest(List<PrayerGroupRole> prayerGroupRoles, SortConfig sortConfig) {
        this.prayerGroupRoles = prayerGroupRoles;
        this.sortConfig = sortConfig;
    }

    public List<PrayerGroupRole> getPrayerGroupRoles() {
        return prayerGroupRoles;
    }

    public void setPrayerGroupRoles(List<PrayerGroupRole> prayerGroupRoles) {
        this.prayerGroupRoles = prayerGroupRoles;
    }

    public SortConfig getSortConfig() {
        return sortConfig;
    }

    public void setSortConfig(SortConfig sortConfig) {
        this.sortConfig = sortConfig;
    }
}
