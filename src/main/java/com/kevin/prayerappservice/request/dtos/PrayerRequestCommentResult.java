package com.kevin.prayerappservice.request.dtos;

import java.time.LocalDateTime;

public class PrayerRequestCommentResult {
    private int prayerRequestCommentId;
    private LocalDateTime submittedDate;
    private int userId;
    private String username;
    private String fullName;
    private Integer imageFileId;
    private String userFileName;
    private String userFileType;
    private String userFileUrl;

    public PrayerRequestCommentResult() {}

    public PrayerRequestCommentResult(int prayerRequestCommentId, LocalDateTime submittedDate, int userId, String username, String fullName, Integer imageFileId, String userFileName, String userFileType, String userFileUrl) {
        this.prayerRequestCommentId = prayerRequestCommentId;
        this.submittedDate = submittedDate;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.imageFileId = imageFileId;
        this.userFileName = userFileName;
        this.userFileType = userFileType;
        this.userFileUrl = userFileUrl;
    }

    public int getPrayerRequestCommentId() {
        return prayerRequestCommentId;
    }

    public void setPrayerRequestCommentId(int prayerRequestCommentId) {
        this.prayerRequestCommentId = prayerRequestCommentId;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getImageFileId() {
        return imageFileId;
    }

    public void setImageFileId(Integer imageFileId) {
        this.imageFileId = imageFileId;
    }

    public String getUserFileName() {
        return userFileName;
    }

    public void setUserFileName(String userFileName) {
        this.userFileName = userFileName;
    }

    public String getUserFileType() {
        return userFileType;
    }

    public void setUserFileType(String userFileType) {
        this.userFileType = userFileType;
    }

    public String getUserFileUrl() {
        return userFileUrl;
    }

    public void setUserFileUrl(String userFileUrl) {
        this.userFileUrl = userFileUrl;
    }
}
