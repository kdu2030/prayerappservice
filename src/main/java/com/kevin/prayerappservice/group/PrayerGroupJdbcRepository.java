package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.dtos.CreatePrayerGroupRequestDTO;
import com.kevin.prayerappservice.group.dtos.CreatedPrayerGroupDTO;
import com.kevin.prayerappservice.group.dtos.PrayerGroupDTO;
import com.kevin.prayerappservice.group.dtos.PrayerGroupSummaryDTO;

import java.util.List;

public interface PrayerGroupJdbcRepository {
    CreatedPrayerGroupDTO createPrayerGroup(CreatePrayerGroupRequestDTO createPrayerGroupRequest);
    List<PrayerGroupSummaryDTO> getPrayerGroupSummariesByUserId(int userId);
    PrayerGroupDTO getPrayerGroup(int prayerGroupId, int userId);
}
