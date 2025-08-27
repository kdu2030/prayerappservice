package com.kevin.prayerappservice.group.models;

public class GroupNameValidationResponse {
    private boolean isError;
    private String[] errors;

    public GroupNameValidationResponse(boolean isError, String[] errors) {
        this.isError = isError;
        this.errors = errors;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }
}
