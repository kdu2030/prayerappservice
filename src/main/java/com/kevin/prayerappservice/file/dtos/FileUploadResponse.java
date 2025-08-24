package com.kevin.prayerappservice.file.dtos;

public class FileUploadResponse {
    private boolean isError;
    private String url;

    public FileUploadResponse(boolean isError, String url) {
        this.isError = isError;
        this.url = url;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
