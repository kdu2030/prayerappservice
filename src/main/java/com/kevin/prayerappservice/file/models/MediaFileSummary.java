package com.kevin.prayerappservice.file.models;

import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.file.entities.FileType;

public class MediaFileSummary {
    private int mediaFileId;
    private String fileName;
    private String fileUrl;
    private FileType fileType;

    public MediaFileSummary(int mediaFileId, String fileName, String fileUrl, FileType fileType) {
        this.mediaFileId = mediaFileId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

    public int getMediaFileId() {
        return mediaFileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public FileType getFileType() {
        return fileType;
    }

    public static MediaFileSummary fileToFileSummary(MediaFile mediaFile){
        return new MediaFileSummary(mediaFile.getMediaFileId(), mediaFile.getFileName(), mediaFile.getFileUrl(), mediaFile.getFileType());
    }
}
