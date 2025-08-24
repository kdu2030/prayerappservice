package com.kevin.prayerappservice.file.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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
}
