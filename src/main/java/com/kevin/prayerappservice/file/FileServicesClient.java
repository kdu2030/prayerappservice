package com.kevin.prayerappservice.file;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServicesClient {
    @Value("${fileupload.url}")
    private String fileUploadBaseUrl;

    private final OkHttpClient httpClient;

    public FileServicesClient(){
        httpClient = new OkHttpClient();
    }

    public Response uploadFile(String filePath, MultipartFile rawFile, String contentType) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",
                        rawFilePath,
                        RequestBody.create(rawFile.getBytes(),
                                MediaType.parse(contentType)))
                .build();

        Request request = new Request.Builder()
                .url(fileUploadBaseUrl + "/file")
                .post(requestBody)
                .build();

        return httpClient.newCall(request).execute();
    }
}
