package com.kevin.prayerappservice.file.models;

import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.file.entities.FileType;

public class FileSummary {
    private int fileId;
    private String fileName;
    private String fileUrl;
    private FileType fileType;

    public FileSummary(int fileId, String fileName, String fileUrl, FileType fileType) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

    public int getFileId() {
        return fileId;
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

    public static FileSummary fileToFileSummary(File file){
        return new FileSummary(file.getFileId(), file.getFileName(), file.getFileUrl(), file.getFileType());
    }
}
