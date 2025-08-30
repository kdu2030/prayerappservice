package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.entities.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Integer>, MediaFileJdbcRepository {
    public Optional<MediaFile> findByFileName(String fileName);
}
