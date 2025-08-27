package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.group.constants.VisibilityLevel;

public class CreatePrayerGroupRequest {
    private String groupName;
    private String description;
    private String rules;
    private Integer avatarFileId;
    private Integer bannerFileId;
    private VisibilityLevel visibilityLevel;

    public CreatePrayerGroupRequest(String groupName, String description, String rules, Integer avatarFileId, Integer bannerFileId, VisibilityLevel visibilityLevel) {
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

    public Integer getAvatarFileId() {
        return avatarFileId;
    }

    public void setAvatarFileId(Integer avatarFileId) {
        this.avatarFileId = avatarFileId;
    }

    public Integer getBannerFileId() {
        return bannerFileId;
    }

    public void setBannerFileId(Integer bannerFileId) {
        this.bannerFileId = bannerFileId;
    }

    public VisibilityLevel getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(VisibilityLevel visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }
}
