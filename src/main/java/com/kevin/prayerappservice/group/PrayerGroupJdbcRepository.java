package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.dtos.*;

import java.sql.SQLException;
import java.util.List;

public interface PrayerGroupJdbcRepository {
    CreatedPrayerGroupDTO createPrayerGroup(CreatePrayerGroupRequestDTO createPrayerGroupRequest);
    List<PrayerGroupSummaryDTO> getPrayerGroupSummariesByUserId(int userId);
    PrayerGroupDTO getPrayerGroup(int prayerGroupId, int userId);
    List<PrayerGroupUserDTO> getPrayerGroupUsers(int prayerGroupId, PrayerGroupRole[] prayerGroupRoles);
    void updatePrayerGroupUsers(PrayerGroupUserUpdateItem[] prayerGroupUserUpdateItems) throws SQLException;
}
