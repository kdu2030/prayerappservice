package com.kevin.prayerappservice.file.entities;

import com.kevin.prayerappservice.group.entities.PrayerGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @OneToMany(mappedBy = "imageFile")
    private List<PrayerGroup> prayerGroupImages;

    public File(String fileName, FileType fileType){
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = null;
    }

    public File(String fileName, FileType fileType,  String fileUrl){
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
