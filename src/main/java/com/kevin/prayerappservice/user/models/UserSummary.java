package com.kevin.prayerappservice.user.models;

import com.kevin.prayerappservice.file.entities.MediaFile;

public class UserSummary {
    private int userId;
    private String username;
    private String emailAddress;
    private String fullName;
    private MediaFile image;
    private UserTokenPair tokens;

    public UserSummary() {}

    public UserSummary(int userId, String username, String emailAddress, String fullName, UserTokenPair tokens) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.tokens = tokens;
        this.username = username;
        image = null;
    }

    public UserSummary(int userId, String username, String emailAddress, String fullName, UserTokenPair tokens, MediaFile image) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.tokens = tokens;
        this.username = username;
        this.image = image;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserTokenPair getTokens() {
        return tokens;
    }

    public void setTokens(UserTokenPair tokens) {
        this.tokens = tokens;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MediaFile getImage() {
        return image;
    }

    public void setImage(MediaFile image) {
        this.image = image;
    }
}
