package com.kevin.prayerappservice.file.entities;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

public enum FileType {
    UNKNOWN,
    IMAGE;

    private static final long MAX_IMAGE_FILE_SIZE = 2 * 1024 * 1024; // 2 MB

    public static FileType getFileTypeFromContentType(@Nullable String contentType){
        if(contentType == null){
            return UNKNOWN;
        }

        return switch (contentType) {
            case MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE -> IMAGE;
            default -> UNKNOWN;
        };
    }

    public static boolean isFileSizeWithinBounds(FileType fileType, long fileSizeInBytes){
        if(fileType == FileType.IMAGE){
            return fileSizeInBytes <= MAX_IMAGE_FILE_SIZE;
        }

        return true;
    }

}
