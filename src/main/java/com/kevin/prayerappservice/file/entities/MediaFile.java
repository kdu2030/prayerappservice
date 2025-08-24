package com.kevin.prayerappservice.file.entities;

import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class MediaFile {
    @Id
    @GeneratedValue
    private Integer mediaFileId;

    @NotBlank
    private String fileName;

    @NotBlank
    @Column(unique = true)
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @OneToMany(mappedBy = "imageFile")
    private List<User> users;

    @OneToMany(mappedBy = "avatarFile")
    private List<PrayerGroup> avatarPrayerGroups;

    @OneToMany(mappedBy = "bannerFile")
    private List<PrayerGroup> bannerPrayerGroups;

    public MediaFile() {}

    public MediaFile(String fileName, FileType fileType){
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = null;
    }

    public MediaFile(String fileName, FileType fileType, String fileUrl){
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

    public @NotBlank String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(@NotBlank String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getMediaFileId() {
        return mediaFileId;
    }

    public @NotBlank String getFileName() {
        return fileName;
    }

    public void setFileName(@NotBlank String fileName) {
        this.fileName = fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public void setMediaFileId(Integer mediaFileId) {
        this.mediaFileId = mediaFileId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<PrayerGroup> getAvatarPrayerGroups() {
        return avatarPrayerGroups;
    }

    public void setAvatarPrayerGroups(List<PrayerGroup> avatarPrayerGroups) {
        this.avatarPrayerGroups = avatarPrayerGroups;
    }

    public List<PrayerGroup> getBannerPrayerGroups() {
        return bannerPrayerGroups;
    }

    public void setBannerPrayerGroups(List<PrayerGroup> bannerPrayerGroups) {
        this.bannerPrayerGroups = bannerPrayerGroups;
    }
}
