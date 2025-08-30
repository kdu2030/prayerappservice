package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.file.entities.MediaFile;

public class PrayerGroupSummaryModel {
    private int prayerGroupId;
    private String groupName;
    private MediaFile avatarFile;

    public PrayerGroupSummaryModel(){}

    public PrayerGroupSummaryModel(int prayerGroupId, String groupName, MediaFile avatarFile) {
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

    public MediaFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MediaFile avatarFile) {
        this.avatarFile = avatarFile;
    }
}
