package com.kevin.prayerappservice.file.entities;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

public enum FileType {
    UNKNOWN,
    IMAGE;

    public static FileType getFileTypeFromContentType(@Nullable String contentType){
        if(contentType == null){
            return UNKNOWN;
        }

        return switch (contentType) {
            case MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE -> IMAGE;
            default -> UNKNOWN;
        };
    }

}
