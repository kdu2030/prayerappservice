package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.entities.PrayerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrayerRequestRepository extends JpaRepository<PrayerRequest, Integer>, PrayerRequestJdbcRepository {
}
