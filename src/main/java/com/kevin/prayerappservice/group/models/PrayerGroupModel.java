package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.entities.PrayerGroupRole;
import com.kevin.prayerappservice.group.entities.VisibilityLevel;
import com.kevin.prayerappservice.user.entities.User;

import java.util.List;

public class PrayerGroupModel {
    private Integer prayerGroupId;
    private String groupName;
    private String description;
    private String rules;
    private MediaFile avatarFile;
    private MediaFile bannerFile;
    private List<User> admins;
    private VisibilityLevel visibilityLevel;
    private boolean isUserJoined;
    private PrayerGroupRole prayerGroupRole;

    public PrayerGroupModel(Integer prayerGroupId, String groupName, String description, String rules, MediaFile avatarFile, MediaFile bannerFile, List<User> admins, VisibilityLevel visibilityLevel, boolean isUserJoined, PrayerGroupRole prayerGroupRole) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.avatarFile = avatarFile;
        this.bannerFile = bannerFile;
        this.admins = admins;
        this.visibilityLevel = visibilityLevel;
        this.isUserJoined = isUserJoined;
        this.prayerGroupRole = prayerGroupRole;
    }

    public Integer getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(Integer prayerGroupId) {
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

    public MediaFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MediaFile avatarFile) {
        this.avatarFile = avatarFile;
    }

    public MediaFile getBannerFile() {
        return bannerFile;
    }

    public void setBannerFile(MediaFile bannerFile) {
        this.bannerFile = bannerFile;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }

    public VisibilityLevel getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(VisibilityLevel visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }

    public boolean isUserJoined() {
        return isUserJoined;
    }

    public void setUserJoined(boolean userJoined) {
        isUserJoined = userJoined;
    }

    public PrayerGroupRole getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(PrayerGroupRole prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }
}
