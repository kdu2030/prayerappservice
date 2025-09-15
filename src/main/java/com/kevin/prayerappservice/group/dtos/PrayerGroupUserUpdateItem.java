package com.kevin.prayerappservice.group.dtos;

import org.postgresql.util.PGobject;

import java.sql.SQLException;

public class PrayerGroupUserUpdateItem extends PGobject {
    private int userId;
    private int prayerGroupId;
    private String prayerGroupRole;

    public PrayerGroupUserUpdateItem(int userId, int prayerGroupId, String prayerGroupRole) throws SQLException {
        setType("prayer_group_user_update_item");
        String rawValue = String.format("(%d,%d,%s)", userId, prayerGroupId, prayerGroupRole);
        setValue(rawValue);

        this.userId = userId;
        this.prayerGroupId = prayerGroupId;
        this.prayerGroupRole = prayerGroupRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(int prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
    }

    public String getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(String prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }
}
