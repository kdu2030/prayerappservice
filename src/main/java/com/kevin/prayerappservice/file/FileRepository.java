package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.entities.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<MediaFile, Integer> {
    public Optional<MediaFile> findByFileName(String fileName);
}
