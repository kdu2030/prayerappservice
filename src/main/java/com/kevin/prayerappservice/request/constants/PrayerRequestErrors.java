package com.kevin.prayerappservice.request.constants;

public class PrayerRequestErrors {
    public static final String USER_MUST_BE_JOINED_TO_CREATE = "A user must be a member of a prayer group to create a prayer request.";
    public static final String USER_MUST_BE_JOINED_TO_VIEW = "Cannot view prayer requests from private prayer groups without membership.";
    public static final String CANNOT_FIND_PRAYER_REQUEST = "Cannot find prayer request";
    public static final String PRAYER_REQUEST_LIKE_EXISTS = "Prayer request like for this user and prayer request already exists.";
    public static final String CANNOT_FIND_PRAYER_REQUEST_LIKE = "Cannot find prayer request like.";
    public static final String ONLY_SUBMITTED_CAN_DELETE_LIKE = "Only the submitted user can delete the prayer request like.";
    public static final String BOOKMARK_ALREADY_EXISTS = "Bookmark already exists for this prayer request and user.";
}
