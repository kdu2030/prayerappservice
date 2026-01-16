package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.entities.PrayerRequestBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrayerRequestBookmarkRepository extends JpaRepository<PrayerRequestBookmark, Integer> {
    Optional<PrayerRequestBookmark> findByPrayerRequest_prayerRequestIdAndUser_userId(int prayerRequestId, int userId);
}
