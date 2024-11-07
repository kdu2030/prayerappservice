package com.kevin.prayerappservice.file.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class File {
    @Id
    @GeneratedValue
    private Integer fileId;

    @NotBlank
    private String fileName;

    @NotBlank
    @Column(unique = true)
    private String fileUrl;

    @Enumerated
    private FileType fileType;

    public File(String fileName, String fileUrl, FileType fileType){
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

    public Integer getFileId() {
        return fileId;
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
