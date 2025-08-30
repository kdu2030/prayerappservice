package com.kevin.prayerappservice.file.dtos;

public class DeleteFileReference {
    private int entityId;
    private String entityType;

    public DeleteFileReference(){}

    public DeleteFileReference(int entityId, String entityType) {
        this.entityId = entityId;
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}
