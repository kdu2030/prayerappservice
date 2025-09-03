package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.group.constants.VisibilityLevel;

public class PutPrayerGroupRequest {
    private String groupName;
    private String description;
    private String rules;
    private VisibilityLevel visibilityLevel;
    private Integer avatarFileId;
    private Integer bannerFileId;

    public PutPrayerGroupRequest() {}

    public PutPrayerGroupRequest(String groupName, String description, String rules, VisibilityLevel visibilityLevel, Integer avatarFileId, Integer bannerFileId) {
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.visibilityLevel = visibilityLevel;
        this.avatarFileId = avatarFileId;
        this.bannerFileId = bannerFileId;
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

    public VisibilityLevel getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(VisibilityLevel visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
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
}

