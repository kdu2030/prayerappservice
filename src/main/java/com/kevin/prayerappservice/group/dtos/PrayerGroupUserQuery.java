package com.kevin.prayerappservice.group.dtos;

public class PrayerGroupUserQuery {
    private int groupId;
    private String[] prayerGroupRoles;

    public PrayerGroupUserQuery() {}

    public PrayerGroupUserQuery(int groupId, String[] prayerGroupRoles) {
        this.groupId = groupId;
        this.prayerGroupRoles = prayerGroupRoles;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String[] getPrayerGroupRoles() {
        return prayerGroupRoles;
    }

    public void setPrayerGroupRoles(String[] prayerGroupRoles) {
        this.prayerGroupRoles = prayerGroupRoles;
    }
}
