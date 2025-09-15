package com.kevin.prayerappservice.group.models;

import java.util.List;

public class PrayerGroupUsersGetResponse {
    private List<PrayerGroupUserModel> prayerGroupUsers;

    public PrayerGroupUsersGetResponse(){}

    public PrayerGroupUsersGetResponse(List<PrayerGroupUserModel> prayerGroupUsers) {
        this.prayerGroupUsers = prayerGroupUsers;
    }

    public List<PrayerGroupUserModel> getPrayerGroupUsers() {
        return prayerGroupUsers;
    }

    public void setPrayerGroupUsers(List<PrayerGroupUserModel> prayerGroupUsers) {
        this.prayerGroupUsers = prayerGroupUsers;
    }
}
