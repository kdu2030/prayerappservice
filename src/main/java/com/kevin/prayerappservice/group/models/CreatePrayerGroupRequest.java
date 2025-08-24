package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.group.entities.VisibilityLevel;

public class CreatePrayerGroupRequest {
    private String groupName;
    private String description;
    private String rules;
    private int avatarFileId;
    private int bannerFileId;
    private VisibilityLevel visibilityLevel;

    public CreatePrayerGroupRequest(String groupName, String description, String rules, int avatarFileId, int bannerFileId, VisibilityLevel visibilityLevel) {
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.avatarFileId = avatarFileId;
        this.bannerFileId = bannerFileId;
        this.visibilityLevel = visibilityLevel;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getAvatarFileId() {
        return avatarFileId;
    }

    public void setAvatarFileId(int avatarFileId) {
        this.avatarFileId = avatarFileId;
    }

    public int getBannerFileId() {
        return bannerFileId;
    }

    public void setBannerFileId(int bannerFileId) {
        this.bannerFileId = bannerFileId;
    }

    public VisibilityLevel getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(VisibilityLevel visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }
}
