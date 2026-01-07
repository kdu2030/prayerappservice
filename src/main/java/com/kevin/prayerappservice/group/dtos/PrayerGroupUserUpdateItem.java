package com.kevin.prayerappservice.group.dtos;

import org.postgresql.util.PGobject;

import java.sql.SQLException;

public class PrayerGroupUserUpdateItem extends PGobject {
    private int userId;
    private String prayerGroupRole;

    public PrayerGroupUserUpdateItem(int userId, String prayerGroupRole) throws SQLException {
        setType("prayer_group_user_update_item");
        String rawValue = String.format("(%d,%s)", userId, prayerGroupRole);
        setValue(rawValue);

        this.userId = userId;
        this.prayerGroupRole = prayerGroupRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(String prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }
}
