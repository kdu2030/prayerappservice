package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.dtos.*;

import java.sql.SQLException;
import java.util.List;

public interface PrayerGroupJdbcRepository {
    CreatedPrayerGroupDTO createPrayerGroup(CreatePrayerGroupRequestDTO createPrayerGroupRequest);
    List<PrayerGroupSummaryDTO> getPrayerGroupSummariesByUserId(int userId);
    PrayerGroupDTO getPrayerGroup(int prayerGroupId, int userId);
    List<PrayerGroupUserDTO> getPrayerGroupUsers(int prayerGroupId, List<PrayerGroupRole> prayerGroupRoles);
    void updatePrayerGroupUsers(int prayerGroupId, PrayerGroupUserUpdateItem[] prayerGroupUserUpdateItems) throws SQLException;
    List<PrayerGroupSummaryDTO> searchPrayerGroups(String groupNameQuery, int maxNumResults);
}
