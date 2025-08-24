package com.kevin.prayerappservice.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.dtos.FileUploadResponse;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.file.entities.FileType;
import com.kevin.prayerappservice.file.models.FileSummary;
import okhttp3.Response;
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
    private final FileServicesClient fileServicesClient;

    @Autowired
    public FileService(ObjectMapper objectMapper, FileRepository fileRepository,
                       FileServicesClient fileServicesClient) {
        this.objectMapper = objectMapper;
        this.fileRepository = fileRepository;
        this.fileServicesClient = fileServicesClient;
    }

    public FileSummary uploadFile(MultipartFile rawFile) throws IOException {
        String contentType = rawFile.getContentType();
        FileType fileType = FileType.getFileTypeFromContentType(contentType);
        String rawFilePath = rawFile.getOriginalFilename();

        if (contentType == null || fileType == FileType.UNKNOWN) {
            throw new DataValidationException(new String[]{"File type is not supported."});
        }

        if (rawFilePath == null) {
            throw new DataValidationException(new String[]{"File must have a name."});
        }

        try (Response response = fileServicesClient.uploadFile(rawFilePath, rawFile, contentType)) {

            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unable to upload file to File API");
            }

            FileUploadResponse fileResponseBody = objectMapper.readValue(response.body().string(),
                    FileUploadResponse.class);
            String fileName = String.valueOf(Paths.get(rawFilePath).getFileName());

            File file = new File(fileName, fileType, fileResponseBody.getUrl());
            fileRepository.save(file);

            return FileSummary.fileToFileSummary(file);
        } catch (Exception e) {
            throw new IOException("Unable to upload file");
        }
    }

    public void deleteFile(int fileId) throws IOException {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new DataValidationException(new String[]{"Unable to find file"}));

        String fileUrl = file.getFileUrl();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        try (Response response = fileServicesClient.deleteFile(fileName)) {
            if (!response.isSuccessful()) {
                throw new IOException(String.format("Unable to delete file %d", fileId));
            }

            fileRepository.delete(file);
        } catch (IOException e) {
            throw new IOException(String.format("Unable to delete file %d", fileId));
        }
    }
}
