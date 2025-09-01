package com.kevin.prayerappservice.group.dtos;

public class PrayerGroupUserDTO {
    private int userId;
    private String fullName;
    private String username;
    private String prayerGroupRole;
    private Integer imageFileId;
    private String fileName;
    private String fileUrl;
    private String fileType;

    public PrayerGroupUserDTO(){}

    public PrayerGroupUserDTO(int userId, String fullName, String username, String prayerGroupRole, Integer imageFileId, String fileName, String fileUrl, String fileType) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.prayerGroupRole = prayerGroupRole;
        this.imageFileId = imageFileId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(String prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }

    public Integer getImageFileId() {
        return imageFileId;
    }

    public void setImageFileId(Integer imageFileId) {
        this.imageFileId = imageFileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
