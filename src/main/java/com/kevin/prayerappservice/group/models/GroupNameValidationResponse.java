package com.kevin.prayerappservice.group.models;

public class GroupNameValidationResponse {
    private boolean isNameValid;
    private String[] errors;

    public GroupNameValidationResponse(boolean isNameValid, String[] errors) {
        this.isNameValid = isNameValid;
        this.errors = errors;
    }

    public boolean isNameValid() {
        return isNameValid;
    }

    public void setNameValid(boolean nameValid) {
        isNameValid = nameValid;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }
}
