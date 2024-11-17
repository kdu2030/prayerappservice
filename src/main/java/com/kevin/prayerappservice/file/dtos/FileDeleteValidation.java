package com.kevin.prayerappservice.file.dtos;

public class FileDeleteValidation {
    private boolean canDelete;
    private String deleteError;

    public FileDeleteValidation(boolean canDelete, String deleteError) {
        this.canDelete = canDelete;
        this.deleteError = deleteError;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String getDeleteError() {
        return deleteError;
    }

    public void setDeleteError(String deleteError) {
        this.deleteError = deleteError;
    }
}
