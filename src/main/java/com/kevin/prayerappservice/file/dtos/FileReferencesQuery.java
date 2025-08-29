package com.kevin.prayerappservice.file.dtos;

public class FileReferencesQuery {
    private int targetFileId;

    public FileReferencesQuery(int targetFileId) {
        this.targetFileId = targetFileId;
    }

    public int getTargetFileId() {
        return targetFileId;
    }

    public void setTargetFileId(int targetFileId) {
        this.targetFileId = targetFileId;
    }
}
