package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.dtos.*;

import java.util.List;

public interface PrayerRequestJdbcRepository {
    PrayerRequestCreateResult createPrayerRequest(PrayerRequestCreateQuery createQuery);
    List<PrayerRequestGetResult> getPrayerRequests(PrayerRequestGetQuery getQuery);
    PrayerRequestCountResult getPrayerRequestsCount(PrayerRequestCountQuery countQuery);
    PrayerRequestGetResult getPrayerRequest(int prayerRequestId, int userId);
    List<PrayerRequestCommentResult> getPrayerRequestComments(int prayerRequestId);
    PrayerRequestCommentResult createPrayerRequestComment(PrayerRequestCommentCreateParams createParams);
    void deletePrayerRequestComment(int prayerRequestCommentId);
    List<PrayerRequestUserCommentResult> getPrayerRequestUserCommentIds(PrayerRequestUserActionIdQuery commentQuery);
    List<PrayerRequestUserSessionResult> getPrayerRequestUserSessionIds(PrayerRequestUserActionIdQuery sessionQuery);
    PrayerRequestGetResult updatePrayerRequest(PrayerRequestUpdateQuery prayerRequestUpdateQuery);
    void deletePrayerRequest(int prayerRequestId);
}
