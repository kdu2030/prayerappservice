package com.kevin.prayerappservice.user.models;

public class UserDetails {
    private int userId;
    private String emailAddress;
    private String fullName;
    private UserTokenPair tokens;

    public UserDetails(int userId, String emailAddress, String fullName, UserTokenPair tokens) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.tokens = tokens;
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
}
