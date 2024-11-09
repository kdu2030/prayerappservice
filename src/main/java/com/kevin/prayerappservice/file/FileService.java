package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.file.entities.FileType;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    @Value("fileupload.url")
    private String fileUploadBaseUrl;

    public File uploadFile(MultipartFile rawFile) throws IOException {
        String contentType = rawFile.getContentType();
        FileType fileType = FileType.getFileTypeFromContentType(contentType);
        if (contentType == null || fileType == FileType.UNKNOWN) {
            throw new DataValidationException(new String[]{"File type is not supported."});
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file",
                        rawFile.getOriginalFilename(),
                        RequestBody.create(rawFile.getBytes(),
                        MediaType.parse(rawFile.getContentType())))
                .build();




        return null;
    }

}
