package com.kevin.prayerappservice.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.dtos.DeleteFileReference;
import com.kevin.prayerappservice.file.dtos.FileUploadResponse;
import com.kevin.prayerappservice.file.entities.FileType;
import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.file.models.MediaFileSummary;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MediaFileService {
    @Value("${fileupload.url}")
    private String fileUploadBaseUrl;

    private final ObjectMapper objectMapper;
    private final MediaFileRepository mediaFileRepository;
    private final FileServicesClient fileServicesClient;

    @Autowired
    public MediaFileService(ObjectMapper objectMapper, MediaFileRepository mediaFileRepository,
                            FileServicesClient fileServicesClient) {
        this.objectMapper = objectMapper;
        this.mediaFileRepository = mediaFileRepository;
        this.fileServicesClient = fileServicesClient;
    }

    public MediaFileSummary uploadFile(MultipartFile rawFile) throws IOException {
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

            MediaFile mediaFile = new MediaFile(fileName, fileType, fileResponseBody.getUrl());
            mediaFileRepository.save(mediaFile);

            return MediaFileSummary.fileToFileSummary(mediaFile);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteFile(int fileId) throws IOException {
        MediaFile mediaFile = mediaFileRepository.findById(fileId)
                .orElseThrow(() -> new DataValidationException(new String[]{"Unable to find file"}));

        List<DeleteFileReference> deleteFileReferences = mediaFileRepository.getFileReferencesForDelete(fileId);
        if (!deleteFileReferences.isEmpty()) {
            throw new DataValidationException(new String[]{getDeleteFileReferencesErrorMessage(deleteFileReferences)});
        }

        String fileUrl = mediaFile.getFileUrl();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        try (Response response = fileServicesClient.deleteFile(fileName)) {
            if (!response.isSuccessful()) {
                throw new IOException(String.format("Unable to delete file %d", fileId));
            }

            mediaFileRepository.delete(mediaFile);
        } catch (IOException e) {
            throw new IOException(String.format("Unable to delete file %d", fileId));
        }
    }

    public String getDeleteFileReferencesErrorMessage(List<DeleteFileReference> references) {
        StringBuilder stringBuilder = new StringBuilder("File cannot be deleted because it is referenced by: ");

        for (int i = 0; i < references.size(); i++) {
            DeleteFileReference reference = references.get(i);
            stringBuilder.append(String.format("%s %d", reference.getEntityType(), reference.getEntityId()));

            if (i < references.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }
}
