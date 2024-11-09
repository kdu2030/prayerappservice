package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.file.entities.FileType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Service
public class FileService {
    @Value("fileupload.url")
    private String fileUploadBaseUrl;

    public File uploadFile(MultipartFile rawFile){
        FileType fileType = FileType.getFileTypeFromContentType(rawFile.getContentType());
        if(fileType == FileType.UNKNOWN){
            throw new DataValidationException(new String[] {"File type is not supported."});
        }

        HttpClient client = HttpClient.newHttpClient();
        URI fileUploadUrl = URI.create(fileUploadBaseUrl + "/file-upload");
        HttpRequest fileUploadRequest = HttpRequest.newBuilder(fileUploadUrl)
                .header(HttpHeaders.CONTENT_TYPE, rawFile.getContentType())
                .header(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON))
                .build();

        return null;
    }

}
