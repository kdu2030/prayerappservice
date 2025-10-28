package com.kevin.prayerappservice.request.dtos;

import com.kevin.prayerappservice.file.entities.FileType;

import java.time.LocalDateTime;

public class PrayerRequestGetResult {
    private int prayerRequestId;
    private String requestTitle;
    private String requestDescription;
    private LocalDateTime createdDate;
    private LocalDateTime expirationDate;
    private int likeCount;
    private int commentCount;
    private int prayedCount;
    private Integer userLikeId;
    private Integer userCommentId;
    private Integer userBookmarkId;
    private Integer userPrayerSessionId;
    private int prayerGroupId;
    private String groupName;
    private Integer avatarFileId;
    private String avatarFileName;
    private String avatarFileUrl;
    private FileType avatarFileType;
    private int userId;
    private String username;
    private Integer userFileId;
    private String userFileName;
    private String userFileUrl;
    private FileType userFileType;

    public PrayerRequestGetResult(){}

    public PrayerRequestGetResult(int prayerRequestId, String requestTitle, String requestDescription, LocalDateTime createdDate, LocalDateTime expirationDate, int likeCount, int commentCount, int prayedCount, Integer userLikeId, Integer userCommentId, Integer userBookmarkId, Integer userPrayerSessionId, int prayerGroupId, String groupName, Integer avatarFileId, String avatarFileName, String avatarFileUrl, FileType avatarFileType, int userId, String username, Integer userFileId, String userFileName, String userFileUrl, FileType userFileType) {
        this.prayerRequestId = prayerRequestId;
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.prayedCount = prayedCount;
        this.userLikeId = userLikeId;
        this.userCommentId = userCommentId;
        this.userBookmarkId = userBookmarkId;
        this.userPrayerSessionId = userPrayerSessionId;
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.avatarFileId = avatarFileId;
        this.avatarFileName = avatarFileName;
        this.avatarFileUrl = avatarFileUrl;
        this.avatarFileType = avatarFileType;
        this.userId = userId;
        this.username = username;
        this.userFileId = userFileId;
        this.userFileName = userFileName;
        this.userFileUrl = userFileUrl;
        this.userFileType = userFileType;
    }

    public int getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(int prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getPrayedCount() {
        return prayedCount;
    }

    public void setPrayedCount(int prayedCount) {
        this.prayedCount = prayedCount;
    }

    public Integer getUserLikeId() {
        return userLikeId;
    }

    public void setUserLikeId(Integer userLikeId) {
        this.userLikeId = userLikeId;
    }

    public Integer getUserCommentId() {
        return userCommentId;
    }

    public void setUserCommentId(Integer userCommentId) {
        this.userCommentId = userCommentId;
    }

    public Integer getUserBookmarkId() {
        return userBookmarkId;
    }

    public void setUserBookmarkId(Integer userBookmarkId) {
        this.userBookmarkId = userBookmarkId;
    }

    public Integer getUserPrayerSessionId() {
        return userPrayerSessionId;
    }

    public void setUserPrayerSessionId(Integer userPrayerSessionId) {
        this.userPrayerSessionId = userPrayerSessionId;
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

    public String getAvatarFileUrl() {
        return avatarFileUrl;
    }

    public void setAvatarFileUrl(String avatarFileUrl) {
        this.avatarFileUrl = avatarFileUrl;
    }

    public FileType getAvatarFileType() {
        return avatarFileType;
    }

    public void setAvatarFileType(FileType avatarFileType) {
        this.avatarFileType = avatarFileType;
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

    public Integer getUserFileId() {
        return userFileId;
    }

    public void setUserFileId(Integer userFileId) {
        this.userFileId = userFileId;
    }

    public String getUserFileName() {
        return userFileName;
    }

    public void setUserFileName(String userFileName) {
        this.userFileName = userFileName;
    }

    public String getUserFileUrl() {
        return userFileUrl;
    }

    public void setUserFileUrl(String userFileUrl) {
        this.userFileUrl = userFileUrl;
    }

    public FileType getUserFileType() {
        return userFileType;
    }

    public void setUserFileType(FileType userFileType) {
        this.userFileType = userFileType;
    }
}
