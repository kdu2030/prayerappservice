package com.kevin.prayerappservice.group.models;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PrayerGroupUserUpdateRequest {
    @NotNull
    private List<PrayerGroupUserUpdateModel> prayerGroupUsers;

    public PrayerGroupUserUpdateRequest(){}

    public PrayerGroupUserUpdateRequest(List<PrayerGroupUserUpdateModel> prayerGroupUsers) {
        this.prayerGroupUsers = prayerGroupUsers;
    }

    public List<PrayerGroupUserUpdateModel> getPrayerGroupUsers() {
        return prayerGroupUsers;
    }

    public void setPrayerGroupUsers(List<PrayerGroupUserUpdateModel> prayerGroupUsers) {
        this.prayerGroupUsers = prayerGroupUsers;
    }
}
