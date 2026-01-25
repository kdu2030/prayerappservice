package com.kevin.prayerappservice.request.models;

import java.time.LocalDateTime;
import java.util.List;

public class PrayerRequestDetailsModel extends PrayerRequestModel {
    private List<PrayerRequestCommentModel> comments;

    public PrayerRequestDetailsModel() {
        super();
    }

    public PrayerRequestDetailsModel(int prayerRequestId, String requestTitle, String requestDescription, LocalDateTime createdDate, LocalDateTime expirationDate, PrayerRequestPrayerGroupSummary prayerGroup, PrayerRequestUserSummary user, int likeCount, int commentCount, int prayedCount, Integer userLikeId, Integer userCommentId, Integer userBookmarkId, Integer userPrayerSessionId, List<PrayerRequestCommentModel> comments) {
        super(prayerRequestId, requestTitle, requestDescription, createdDate, expirationDate, prayerGroup, user, likeCount, commentCount, prayedCount, userLikeId, userCommentId, userBookmarkId, userPrayerSessionId);
        this.comments = comments;
    }

    public List<PrayerRequestCommentModel> getComments() {
        return comments;
    }

    public void setComments(List<PrayerRequestCommentModel> comments) {
        this.comments = comments;
    }

    public static PrayerRequestDetailsModel prayerRequestModelToDetailsModel(PrayerRequestModel prayerRequestModel, List<PrayerRequestCommentModel> comments) {
        return new PrayerRequestDetailsModel(
                prayerRequestModel.getPrayerRequestId(),
                prayerRequestModel.getRequestTitle(),
                prayerRequestModel.getRequestDescription(),
                prayerRequestModel.getCreatedDate(),
                prayerRequestModel.getExpirationDate(),
                prayerRequestModel.getPrayerGroup(),
                prayerRequestModel.getUser(),
                prayerRequestModel.getLikeCount(),
                prayerRequestModel.getCommentCount(),
                prayerRequestModel.getPrayedCount(),
                prayerRequestModel.getUserLikeId(),
                prayerRequestModel.getUserCommentId(),
                prayerRequestModel.getUserBookmarkId(),
                prayerRequestModel.getUserPrayerSessionId(),
                comments
        );
    }
}
