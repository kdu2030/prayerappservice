package com.kevin.prayerappservice.group.dtos;

import com.kevin.prayerappservice.group.entities.VisibilityLevel;

public class CreatedPrayerGroupDTO {
    private int prayerGroupId;
    private String groupName;
    private String description;
    private String rules;
    private VisibilityLevel visibilityLevel;
    private int avatarFileId;
    private String groupAvatarFileName;
    private String groupAvatarFileUrl;
    private int bannerFileId;
    private String groupBannerFileName;
    private String groupBannerFileUrl;
    private int adminUserId;
    private String adminFullName;
    private int adminImageFileId;
    private int adminImageFile;

    public CreatedPrayerGroupDTO(int prayerGroupId, String groupName, String description, String rules, VisibilityLevel visibilityLevel, int avatarFileId, String groupAvatarFileName, String groupAvatarFileUrl, int bannerFileId, String groupBannerFileName, String groupBannerFileUrl, int adminUserId, String adminFullName, int adminImageFileId, int adminImageFile) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.visibilityLevel = visibilityLevel;
        this.avatarFileId = avatarFileId;
        this.groupAvatarFileName = groupAvatarFileName;
        this.groupAvatarFileUrl = groupAvatarFileUrl;
        this.bannerFileId = bannerFileId;
        this.groupBannerFileName = groupBannerFileName;
        this.groupBannerFileUrl = groupBannerFileUrl;
        this.adminUserId = adminUserId;
        this.adminFullName = adminFullName;
        this.adminImageFileId = adminImageFileId;
        this.adminImageFile = adminImageFile;
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

    public int getAvatarFileId() {
        return avatarFileId;
    }

    public void setAvatarFileId(int avatarFileId) {
        this.avatarFileId = avatarFileId;
    }

    public String getGroupAvatarFileName() {
        return groupAvatarFileName;
    }

    public void setGroupAvatarFileName(String groupAvatarFileName) {
        this.groupAvatarFileName = groupAvatarFileName;
    }

    public String getGroupAvatarFileUrl() {
        return groupAvatarFileUrl;
    }

    public void setGroupAvatarFileUrl(String groupAvatarFileUrl) {
        this.groupAvatarFileUrl = groupAvatarFileUrl;
    }

    public int getBannerFileId() {
        return bannerFileId;
    }

    public void setBannerFileId(int bannerFileId) {
        this.bannerFileId = bannerFileId;
    }

    public String getGroupBannerFileName() {
        return groupBannerFileName;
    }

    public void setGroupBannerFileName(String groupBannerFileName) {
        this.groupBannerFileName = groupBannerFileName;
    }

    public String getGroupBannerFileUrl() {
        return groupBannerFileUrl;
    }

    public void setGroupBannerFileUrl(String groupBannerFileUrl) {
        this.groupBannerFileUrl = groupBannerFileUrl;
    }

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminFullName() {
        return adminFullName;
    }

    public void setAdminFullName(String adminFullName) {
        this.adminFullName = adminFullName;
    }

    public int getAdminImageFileId() {
        return adminImageFileId;
    }

    public void setAdminImageFileId(int adminImageFileId) {
        this.adminImageFileId = adminImageFileId;
    }

    public int getAdminImageFile() {
        return adminImageFile;
    }

    public void setAdminImageFile(int adminImageFile) {
        this.adminImageFile = adminImageFile;
    }
}
