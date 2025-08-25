package com.kevin.prayerappservice.group.dtos;

import com.kevin.prayerappservice.group.entities.VisibilityLevel;

public class CreatePrayerGroupRequestDTO {
    private String creatorUserId;
    private String newGroupName;
    private String groupDescription;
    private String groupRules;
    private VisibilityLevel groupVisibility;
    private Integer avatarFileId;
    private Integer bannerFileId;

    public CreatePrayerGroupRequestDTO(String creatorUserId, String newGroupName, String groupDescription, String groupRules, VisibilityLevel groupVisibility, Integer avatarFileId, Integer bannerFileId) {
        this.creatorUserId = creatorUserId;
        this.newGroupName = newGroupName;
        this.groupDescription = groupDescription;
        this.groupRules = groupRules;
        this.groupVisibility = groupVisibility;
        this.avatarFileId = avatarFileId;
        this.bannerFileId = bannerFileId;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getNewGroupName() {
        return newGroupName;
    }

    public void setNewGroupName(String newGroupName) {
        this.newGroupName = newGroupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupRules() {
        return groupRules;
    }

    public void setGroupRules(String groupRules) {
        this.groupRules = groupRules;
    }

    public VisibilityLevel getGroupVisibility() {
        return groupVisibility;
    }

    public void setGroupVisibility(VisibilityLevel groupVisibility) {
        this.groupVisibility = groupVisibility;
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
