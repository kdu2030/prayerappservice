package com.kevin.prayerappservice.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.dtos.FileDeleteValidation;
import com.kevin.prayerappservice.file.dtos.FileUploadResponse;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.file.entities.FileType;
import com.kevin.prayerappservice.file.models.FileSummary;
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

    public FileSummary uploadFile(MultipartFile rawFile) throws IOException {
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
                .url(fileUploadBaseUrl + "/file")
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
        return FileSummary.fileToFileSummary(file);
    }

    public void deleteFile(int fileId) throws IOException{
        FileDeleteValidation fileDeleteValidation = validateFileDelete(fileId);
        if(!fileDeleteValidation.isCanDelete()) {
            throw new DataValidationException(new String[]{fileDeleteValidation.getDeleteError()});
        }

        File file = fileRepository.findById(fileId).get();
        String fileUrl = file.getFileUrl();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(String.format("%s/file/%s", fileUploadBaseUrl, fileName))
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()){
            throw new IOException("Unable to delete file");
        }

        fileRepository.delete(file);
    }

    private FileDeleteValidation validateFileDelete(int fileId){
        Object[][] result = fileRepository.validateFileDeleteRaw(fileId);
        return new FileDeleteValidation((boolean) result[0][0], (String) result[0][1]);
    }

}
