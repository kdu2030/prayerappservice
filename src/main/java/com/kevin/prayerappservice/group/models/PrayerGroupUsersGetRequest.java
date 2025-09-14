package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.common.SortConfig;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.constants.PrayerGroupUserSortField;

import java.util.List;

public class PrayerGroupUsersGetRequest {
    private List<PrayerGroupRole> prayerGroupRoles;
    private SortConfig<PrayerGroupUserSortField> sortConfig;

    public PrayerGroupUsersGetRequest(){}

    public PrayerGroupUsersGetRequest(List<PrayerGroupRole> prayerGroupRoles, SortConfig<PrayerGroupUserSortField> sortConfig) {
        this.prayerGroupRoles = prayerGroupRoles;
        this.sortConfig = sortConfig;
    }

    public List<PrayerGroupRole> getPrayerGroupRoles() {
        return prayerGroupRoles;
    }

    public void setPrayerGroupRoles(List<PrayerGroupRole> prayerGroupRoles) {
        this.prayerGroupRoles = prayerGroupRoles;
    }

    public SortConfig<PrayerGroupUserSortField> getSortConfig() {
        return sortConfig;
    }

    public void setSortConfig(SortConfig<PrayerGroupUserSortField> sortConfig) {
        this.sortConfig = sortConfig;
    }
}
