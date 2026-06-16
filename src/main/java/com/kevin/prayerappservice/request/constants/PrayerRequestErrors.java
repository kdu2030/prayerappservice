package com.kevin.prayerappservice.request.constants;

public class PrayerRequestErrors {
    public static final String USER_MUST_BE_JOINED_TO_CREATE = "A user must be a member of the prayer group to create a prayer request.";
    public static final String USER_MUST_BE_JOINED_TO_VIEW = "Cannot view prayer requests from private prayer groups without membership.";
    public static final String CANNOT_FIND_PRAYER_REQUEST = "Cannot find prayer request";
    public static final String PRAYER_REQUEST_LIKE_EXISTS = "Prayer request like for this user and prayer request already exists.";
    public static final String CANNOT_FIND_PRAYER_REQUEST_LIKE = "Cannot find prayer request like.";
    public static final String ONLY_SUBMITTED_CAN_DELETE_LIKE = "Only the submitted user can delete the prayer request like.";
    public static final String BOOKMARK_ALREADY_EXISTS = "Bookmark already exists for this prayer request and user.";
    public static final String CANNOT_FIND_BOOKMARK = "Cannot find bookmark";
    public static final String ONLY_SUBMITTED_CAN_DELETE_BOOKMARK = "Only the submitted user can delete the bookmark.";
    public static final String USER_MUST_BE_JOINED_TO_COMMENT = "A user must be a member of the prayer group to comment on prayer requests.";
    public static final String USER_ID_DOES_NOT_MATCH = "User ID from Auth Token must match User ID in request";
    public static final String ONLY_SUBMITTED_CAN_UPDATE_COMMENT = "Only the submitted user can update the comment.";
    public static final String CANNOT_FIND_PRAYER_REQUEST_COMMENT = "Cannot find prayer request comment.";
    public static final String ONLY_SUBMITTED_CAN_DELETE_COMMENT = "Only the submited user can delete the comment";
    public static final String ONLY_SUBMITTED_CAN_UPDATE_REQUEST = "Only the submitted user can update the prayer request.";
    public static final String ONLY_SUBMITTED_OR_ADMIN_CAN_DELETE_REQUEST = "Only the submitted user or an admin can delete the prayer request.";
}
