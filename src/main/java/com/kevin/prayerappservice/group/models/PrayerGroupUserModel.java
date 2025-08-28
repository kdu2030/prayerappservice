package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.user.models.UserSummary;
import com.kevin.prayerappservice.user.models.UserTokenPair;

public class PrayerGroupUserModel extends UserSummary {
    private PrayerGroupRole prayerGroupRole;

    public PrayerGroupUserModel() {
        super();
    }

    public PrayerGroupUserModel(int userId, String username, String emailAddress, String fullName, UserTokenPair tokens, MediaFile image, PrayerGroupRole prayerGroupRole) {
        super(userId, username, emailAddress, fullName, tokens, image, null);
        this.prayerGroupRole = prayerGroupRole;
    }

    public PrayerGroupRole getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(PrayerGroupRole prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }
}
