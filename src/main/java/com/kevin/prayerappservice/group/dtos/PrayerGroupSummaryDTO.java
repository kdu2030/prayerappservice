package com.kevin.prayerappservice.group.dtos;

import com.kevin.prayerappservice.group.constants.JoinStatus;

public class PrayerGroupSummaryDTO {
    private int prayerGroupId;
    private String groupName;
    private Integer mediaFileId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private JoinStatus joinStatus;

    public PrayerGroupSummaryDTO(){}

    public PrayerGroupSummaryDTO(int prayerGroupId, String groupName, Integer mediaFileId, String fileName, String fileUrl, String fileType, JoinStatus joinStatus) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.mediaFileId = mediaFileId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.joinStatus = joinStatus;
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

    public JoinStatus getJoinStatus(){
        return joinStatus;
    }

    public void setJoinStatus(JoinStatus joinStatus){
        this.joinStatus = joinStatus;
    }
}
