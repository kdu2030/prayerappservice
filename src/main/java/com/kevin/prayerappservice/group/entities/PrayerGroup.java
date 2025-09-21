package com.kevin.prayerappservice.group.entities;

import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.join.entities.JoinRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
public class PrayerGroup {
    @Id
    @GeneratedValue
    private Integer prayerGroupId;

    @NotBlank
    @Column(unique = true)
    private String groupName;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String rules;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VisibilityLevel visibilityLevel;

    @ManyToOne
    @JoinColumn(name = "avatar_file_id")
    private MediaFile avatarFile;

    @ManyToOne
    @JoinColumn(name = "banner_file_id")
    private MediaFile bannerFile;

    @OneToMany(mappedBy = "prayerGroup")
    private List<PrayerGroupUser> prayerGroupUsers;

    @OneToMany(mappedBy = "prayerGroup")
    private List<JoinRequest> joinRequests;

    public PrayerGroup() {
    }

    public PrayerGroup(String groupName, String description, String rules, VisibilityLevel visibilityLevel,
                       MediaFile avatarFile, MediaFile bannerFile) {
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.visibilityLevel = visibilityLevel;
        this.avatarFile = avatarFile;
        this.bannerFile = bannerFile;
    }

    public PrayerGroup(Integer prayerGroupId, String groupName, String description, String rules,
                       VisibilityLevel visibilityLevel, MediaFile avatarFile, MediaFile bannerFile) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.visibilityLevel = visibilityLevel;
        this.avatarFile = avatarFile;
        this.bannerFile = bannerFile;
    }

    public Integer getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(Integer prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
    }

    public @NotBlank String getGroupName() {
        return groupName;
    }

    public void setGroupName(@NotBlank String groupName) {
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

    public @NotNull VisibilityLevel getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(@NotNull VisibilityLevel visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
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

    public List<PrayerGroupUser> getPrayerGroupUsers() {
        return prayerGroupUsers;
    }

    public void setPrayerGroupUsers(List<PrayerGroupUser> prayerGroupUsers) {
        this.prayerGroupUsers = prayerGroupUsers;
    }

    public List<JoinRequest> getJoinRequests() {
        return joinRequests;
    }

    public void setJoinRequests(List<JoinRequest> joinRequests) {
        this.joinRequests = joinRequests;
    }
}
