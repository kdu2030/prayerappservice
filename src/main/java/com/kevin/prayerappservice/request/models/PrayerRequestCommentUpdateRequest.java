package com.kevin.prayerappservice.request.models;

public class PrayerRequestCommentUpdateRequest {
    private String comment;

    public PrayerRequestCommentUpdateRequest(){}

    public PrayerRequestCommentUpdateRequest(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
