package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.join.entities.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Integer>, JoinRequestJdbcRepository {
    Optional<JoinRequest> findByPrayerGroup_prayerGroupId(int prayerGroupId);
}
