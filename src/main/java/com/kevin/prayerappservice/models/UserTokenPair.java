package com.kevin.prayerappservice.models;

import lombok.*;

import java.io.Serializable;


public class UserTokenPair implements Serializable {
    private String authToken;
    private String refreshToken;

    public UserTokenPair(String authToken, String refreshToken) {
        this.authToken = authToken;
        this.refreshToken = refreshToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
