package com.kevin.prayerappservice.group.dtos;

public class PrayerGroupDTO {
    private int prayerGroupId;
    private String groupName;
    private String description;
    private String rules;
    private String visibilityLevel;
    private Integer avatarFileId;
    private String avatarFileName;
    private String avatarfileUrl;
    private String avatarFileType;
    private Integer bannerFileId;
    private String bannerFileName;
    private String bannerfileUrl;
    private String bannerFileType;
    private String prayerGroupRole;
    private Integer joinRequestId;

    public PrayerGroupDTO() {
    }

    public PrayerGroupDTO(int prayerGroupId, String groupName, String description, String rules,
                          String visibilityLevel, Integer avatarFileId, String avatarFileName, String avatarfileUrl,
                          String avatarFileType, Integer bannerFileId, String bannerFileName, String bannerfileUrl,
                          String bannerFileType, String prayerGroupRole, Integer joinRequestId) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.visibilityLevel = visibilityLevel;
        this.avatarFileId = avatarFileId;
        this.avatarFileName = avatarFileName;
        this.avatarfileUrl = avatarfileUrl;
        this.avatarFileType = avatarFileType;
        this.bannerFileId = bannerFileId;
        this.bannerFileName = bannerFileName;
        this.bannerfileUrl = bannerfileUrl;
        this.bannerFileType = bannerFileType;
        this.prayerGroupRole = prayerGroupRole;
        this.joinRequestId = joinRequestId;
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

    public String getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(String visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }

    public Integer getAvatarFileId() {
        return avatarFileId;
    }

    public void setAvatarFileId(Integer avatarFileId) {
        this.avatarFileId = avatarFileId;
    }

    public String getAvatarFileName() {
        return avatarFileName;
    }

    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }

    public String getAvatarfileUrl() {
        return avatarfileUrl;
    }

    public void setAvatarfileUrl(String avatarfileUrl) {
        this.avatarfileUrl = avatarfileUrl;
    }

    public String getAvatarFileType() {
        return avatarFileType;
    }

    public void setAvatarFileType(String avatarFileType) {
        this.avatarFileType = avatarFileType;
    }

    public Integer getBannerFileId() {
        return bannerFileId;
    }

    public void setBannerFileId(Integer bannerFileId) {
        this.bannerFileId = bannerFileId;
    }

    public String getBannerFileName() {
        return bannerFileName;
    }

    public void setBannerFileName(String bannerFileName) {
        this.bannerFileName = bannerFileName;
    }

    public String getBannerfileUrl() {
        return bannerfileUrl;
    }

    public void setBannerfileUrl(String bannerfileUrl) {
        this.bannerfileUrl = bannerfileUrl;
    }

    public String getBannerFileType() {
        return bannerFileType;
    }

    public void setBannerFileType(String bannerFileType) {
        this.bannerFileType = bannerFileType;
    }

    public String getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(String prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }

    public Integer getJoinRequestId() {
        return joinRequestId;
    }

    public void setJoinRequestId(Integer joinRequestId) {
        this.joinRequestId = joinRequestId;
    }
}
