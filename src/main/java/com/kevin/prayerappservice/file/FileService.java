package com.kevin.prayerappservice.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.dtos.FileDeleteValidation;
import com.kevin.prayerappservice.file.dtos.FileUploadResponse;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.file.entities.FileType;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class FileService {
    @Value("${fileupload.url}")
    private String fileUploadBaseUrl;

    private final ObjectMapper objectMapper;
    private final FileRepository fileRepository;

    @Autowired
    public FileService(ObjectMapper objectMapper, FileRepository fileRepository){
        this.objectMapper = objectMapper;
        this.fileRepository = fileRepository;
    }

    public File uploadFile(MultipartFile rawFile) throws IOException {
        String contentType = rawFile.getContentType();
        FileType fileType = FileType.getFileTypeFromContentType(contentType);
        String rawFilePath = rawFile.getOriginalFilename();

        if (contentType == null || fileType == FileType.UNKNOWN) {
            throw new DataValidationException(new String[]{"File type is not supported."});
        }

        if(rawFilePath == null){
            throw new DataValidationException(new String[]{"File must have a name."});
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",
                        rawFilePath,
                        RequestBody.create(rawFile.getBytes(),
                                MediaType.parse(contentType)))
                .build();

        Request request = new Request.Builder()
                .url(fileUploadBaseUrl + "/file-upload")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Unable to upload file to File API");
        }

        FileUploadResponse fileResponseBody = objectMapper.readValue(response.body().string(), FileUploadResponse.class);
        String fileName = String.valueOf(Paths.get(rawFilePath).getFileName());

        File file = new File(fileName, fileType, fileResponseBody.getUrl());
        fileRepository.save(file);
        return file;
    }

    public void deleteFile(int fileId){
        FileDeleteValidation fileDeleteValidation = validateFileDelete(fileId);
        if(!fileDeleteValidation.isCanDelete()){
            throw new DataValidationException(new String[] {fileDeleteValidation.getDeleteError()});
        }


    }

    private FileDeleteValidation validateFileDelete(int fileId){
        Object[][] result = fileRepository.validateFileDeleteRaw(fileId);
        return new FileDeleteValidation((boolean) result[0][0], (String) result[0][1]);
    }

}
