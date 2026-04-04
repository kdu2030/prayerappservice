package com.kevin.prayerappservice.request.dtos;

public class PrayerRequestCommentDeleteQuery {
    private int commentId;

    public PrayerRequestCommentDeleteQuery(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
