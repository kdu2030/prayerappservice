package com.kevin.prayerappservice.request.models;

import com.kevin.prayerappservice.file.models.MediaFileSummary;

public class PrayerRequestUserSummary {
    private int userId;
    private String username;
    private String fullName;
    private MediaFileSummary image;

    public PrayerRequestUserSummary(){}

    public PrayerRequestUserSummary(int userId, String username, String fullName, MediaFileSummary image) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public MediaFileSummary getImage() {
        return image;
    }

    public void setImage(MediaFileSummary image) {
        this.image = image;
    }
}
