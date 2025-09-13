package com.kevin.prayerappservice.group.constants;

public class PrayerGroupErrorMessages {
    public static final String CANNOT_FIND_PRAYER_GROUP = "Cannot find prayer group with prayerGroupId %d";
    public static final String CANNOT_ADD_TO_PRIVATE_GROUP = "Cannot directly add a user to a PRIVATE prayer group. Create a request instead.";
    public static final String MUST_BE_ADMIN_TO_ADD = "User must be an admin of the prayer group to add users other than themselves.";
    public static final String MUST_BE_ADMIN_TO_REMOVE_USER = "User must be an admin of the prayer group to remove a user other than themselves.";
    public static final String USER_DOES_NOT_BELONG_TO_PRAYER_GROUP = "A user with userId %d does not belong to prayer group %d";
    public static final String MUST_BE_ADMIN_TO_UPDATE_USERS = "User must be an admin of a prayer group to update all prayer group users.";
}
