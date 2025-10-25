package com.kevin.prayerappservice.request.models;

import com.kevin.prayerappservice.file.models.MediaFileSummary;

public class PrayerRequestPrayerGroupSummary {
    private int prayerGroupId;
    private String groupName;
    private MediaFileSummary avatarFile;

    public PrayerRequestPrayerGroupSummary(){}

    public PrayerRequestPrayerGroupSummary(int prayerGroupId, String groupName, MediaFileSummary avatarFile) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.avatarFile = avatarFile;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(int prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public MediaFileSummary getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MediaFileSummary avatarFile) {
        this.avatarFile = avatarFile;
    }
}
