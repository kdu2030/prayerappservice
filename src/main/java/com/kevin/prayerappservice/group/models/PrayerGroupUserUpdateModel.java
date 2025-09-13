package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.group.constants.PrayerGroupRole;

public class PrayerGroupUserUpdateModel {
    private int userId;
    private PrayerGroupRole prayerGroupRole;

    public PrayerGroupUserUpdateModel(){}

    public PrayerGroupUserUpdateModel(int userId, PrayerGroupRole prayerGroupRole) {
        this.userId = userId;
        this.prayerGroupRole = prayerGroupRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PrayerGroupRole getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(PrayerGroupRole prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }
}
