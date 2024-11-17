package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    @Query(value = "SELECT can_delete, delete_error FROM validate_file_delete(:fileId)", nativeQuery = true)
    Object[][] validateFileDeleteRaw(@Param("fileId") int fileId);
}
