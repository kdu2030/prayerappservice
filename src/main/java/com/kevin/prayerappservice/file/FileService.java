package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.file.entities.FileType;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    @Value("${fileupload.url}")
    private String fileUploadBaseUrl;

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public File uploadFile(MultipartFile rawFile) throws IOException {
        String contentType = rawFile.getContentType();
        FileType fileType = FileType.getFileTypeFromContentType(contentType);
        if (contentType == null || fileType == FileType.UNKNOWN) {
            throw new DataValidationException(new String[]{"File type is not supported."});
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",
                        rawFile.getOriginalFilename(),
                        RequestBody.create(rawFile.getBytes(),
                                MediaType.parse(rawFile.getContentType())))
                .build();

        Request request = new Request.Builder()
                .url(fileUploadBaseUrl + "/file-upload")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unable to upload file to File API");
        }

        return null;
    }

}
