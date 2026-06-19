package com.kevin.prayerappservice.group.models;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;

public class PrayerGroupUserUpdateRequest {
    @NotNull
    private List<PrayerGroupUserUpdateModel> prayerGroupUsers;
    private OffsetDateTime updateDate;

    public PrayerGroupUserUpdateRequest(){}

    public PrayerGroupUserUpdateRequest(List<PrayerGroupUserUpdateModel> prayerGroupUsers, OffsetDateTime updateDate) {
        this.prayerGroupUsers = prayerGroupUsers;
    }

    public List<PrayerGroupUserUpdateModel> getPrayerGroupUsers() {
        return prayerGroupUsers;
    }

    public void setPrayerGroupUsers(List<PrayerGroupUserUpdateModel> prayerGroupUsers) {
        this.prayerGroupUsers = prayerGroupUsers;
    }

    public OffsetDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(OffsetDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
