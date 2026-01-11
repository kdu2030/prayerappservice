package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.entities.PrayerRequestLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrayerRequestLikeRepository extends JpaRepository<PrayerRequestLike, Integer> {
    Optional<PrayerRequestLike> findByPrayerRequest_prayerRequestIdAndUser_userId(int prayerRequestId, int userId);
}
