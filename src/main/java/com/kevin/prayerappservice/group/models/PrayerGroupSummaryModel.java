package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.constants.JoinStatus;

public class PrayerGroupSummaryModel {
    private int prayerGroupId;
    private String groupName;
    private MediaFile avatarFile;
    private JoinStatus joinStatus;

    public PrayerGroupSummaryModel(){}

    public PrayerGroupSummaryModel(int prayerGroupId, String groupName, MediaFile avatarFile, JoinStatus joinStatus) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.avatarFile = avatarFile;
        this.joinStatus = joinStatus;
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

    public JoinStatus getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(JoinStatus joinStatus) {
        this.joinStatus = joinStatus;
    }
}
