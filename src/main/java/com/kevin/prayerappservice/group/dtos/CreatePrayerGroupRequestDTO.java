package com.kevin.prayerappservice.group.dtos;

import java.time.OffsetDateTime;

public class CreatePrayerGroupRequestDTO {
    private int creatorUserId;
    private String newGroupName;
    private String groupDescription;
    private String groupRules;
    private String groupVisibility;
    private Integer avatarFileId;
    private Integer bannerFileId;
    private OffsetDateTime createdDate;

    public CreatePrayerGroupRequestDTO(int creatorUserId, String newGroupName, String groupDescription, String groupRules, String groupVisibility, Integer avatarFileId, Integer bannerFileId, OffsetDateTime createdDate) {
        this.creatorUserId = creatorUserId;
        this.newGroupName = newGroupName;
        this.groupDescription = groupDescription;
        this.groupRules = groupRules;
        this.groupVisibility = groupVisibility;
        this.avatarFileId = avatarFileId;
        this.bannerFileId = bannerFileId;
        this.createdDate = createdDate;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
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

    public String getGroupVisibility() {
        return groupVisibility;
    }

    public void setGroupVisibility(String groupVisibility) {
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

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
