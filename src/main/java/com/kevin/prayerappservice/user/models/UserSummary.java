package com.kevin.prayerappservice.user.models;

public class UserSummary {
    private int userId;
    private String username;
    private String emailAddress;
    private String fullName;
    private UserTokenPair tokens;

    public UserSummary(int userId, String username, String emailAddress, String fullName, UserTokenPair tokens) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.tokens = tokens;
        this.username = username;
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
}
