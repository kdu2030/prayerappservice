package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.file.models.MediaFileSummary;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.user.models.UserSummary;
import com.kevin.prayerappservice.user.models.UserTokenPair;

import java.time.OffsetDateTime;

public class PrayerGroupUserModel extends UserSummary {
    private PrayerGroupRole prayerGroupRole;
    private OffsetDateTime joinDate;

    public PrayerGroupUserModel() {
        super();
    }

    public PrayerGroupUserModel(int userId, String username, String emailAddress, String fullName, UserTokenPair tokens, MediaFileSummary image, PrayerGroupRole prayerGroupRole, OffsetDateTime joinDate) {
        super(userId, username, emailAddress, fullName, tokens, image, null);
        this.prayerGroupRole = prayerGroupRole;
        this.joinDate = joinDate;
    }

    public PrayerGroupRole getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(PrayerGroupRole prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }

    public OffsetDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(OffsetDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
