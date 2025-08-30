package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.dtos.DeleteFileReference;

import java.util.List;

public interface MediaFileJdbcRepository {
    List<DeleteFileReference> getFileReferencesForDelete(int targetFileId);
}
