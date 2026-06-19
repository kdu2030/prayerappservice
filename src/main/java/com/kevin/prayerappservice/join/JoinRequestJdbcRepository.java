package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.join.dtos.JoinRequestDTO;

import java.time.OffsetDateTime;
import java.util.List;

public interface JoinRequestJdbcRepository {
    List<JoinRequestDTO> getJoinRequests(int prayerGroupId);
    void deleteJoinRequests(int prayerGroupId, List<Integer> joinRequestIds);
    void approveJoinRequests(int prayerGroupId, List<Integer> joinRequestIds, OffsetDateTime approveDate);
}
