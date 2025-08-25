package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.dtos.CreatePrayerGroupRequestDTO;
import com.kevin.prayerappservice.group.dtos.CreatedPrayerGroupDTO;

public interface PrayerGroupJdbcRepository {
    CreatedPrayerGroupDTO createPrayerGroup(CreatePrayerGroupRequestDTO createPrayerGroupRequest);
}
