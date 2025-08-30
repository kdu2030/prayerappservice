package com.kevin.prayerappservice.group.dtos;

public class PrayerGroupSummaryDTO {
    private int prayerGroupId;
    private String groupName;
    private Integer mediaFileId;
    private String fileName;
    private String fileUrl;
    private String fileType;

    public PrayerGroupSummaryDTO(){}

    public PrayerGroupSummaryDTO(int prayerGroupId, String groupName, Integer mediaFileId, String fileName, String fileUrl, String fileType) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.mediaFileId = mediaFileId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
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
}
