package com.kevin.prayerappservice.join.dtos;

import java.time.LocalDateTime;

public class JoinRequestDTO {
    private int joinRequestId;
    private int prayerGroupId;
    private LocalDateTime submittedDate;
    private int userId;
    private String fullName;
    private String username;
    private Integer mediaFileId;
    private String fileName;
    private String fileUrl;
    private String fileType;

    public JoinRequestDTO(){}

    public JoinRequestDTO(int joinRequestId, int prayerGroupId, LocalDateTime submittedDate, int userId, String fullName, String username, Integer mediaFileId, String fileName, String fileUrl, String fileType) {
        this.joinRequestId = joinRequestId;
        this.prayerGroupId = prayerGroupId;
        this.submittedDate = submittedDate;
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.mediaFileId = mediaFileId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

    public int getJoinRequestId() {
        return joinRequestId;
    }

    public void setJoinRequestId(int joinRequestId) {
        this.joinRequestId = joinRequestId;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(int prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
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

    public Integer getMediaFileId() {
        return mediaFileId;
    }

    public void setMediaFileId(Integer mediaFileId) {
        this.mediaFileId = mediaFileId;
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

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
