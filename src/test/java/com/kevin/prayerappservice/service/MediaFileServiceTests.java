package com.kevin.prayerappservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.FileServicesClient;
import com.kevin.prayerappservice.file.MediaFileJdbcRepositoryImpl;
import com.kevin.prayerappservice.file.MediaFileRepository;
import com.kevin.prayerappservice.file.MediaFileService;
import com.kevin.prayerappservice.file.dtos.DeleteFileReference;
import com.kevin.prayerappservice.file.dtos.FileUploadResponse;
import com.kevin.prayerappservice.file.entities.FileType;
import com.kevin.prayerappservice.file.entities.MediaFile;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
public class MediaFileServiceTests {
    @MockBean
    private MediaFileJdbcRepositoryImpl mockMediaFileJdbcRepository;

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileServicesClient mockFileServicesClient;

    @Autowired
    private MediaFileService mediaFileService;

    @Test
    @DirtiesContext
    public void uploadFile_validImage_savesImage() throws IOException {
        FileUploadResponse mockFileUploadResponse = new FileUploadResponse(false, "https://testurl.com/image.png");
        String rawResponseBody = objectMapper.writeValueAsString(mockFileUploadResponse);

        ResponseBody mockResponseBody = ResponseBody.create(rawResponseBody,
                okhttp3.MediaType.get(MediaType.APPLICATION_JSON_VALUE));

        Response mockUploadResponse = Mockito.mock(Response.class);
        Mockito.when(mockUploadResponse.isSuccessful()).thenReturn(true);
        Mockito.when(mockUploadResponse.body()).thenReturn(mockResponseBody);


        Mockito.when(mockFileServicesClient.uploadFile(anyString(), any(MultipartFile.class), anyString()))
                .thenReturn(mockUploadResponse);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("jake_peralta.png", "jake_peralta.png",
                MediaType.IMAGE_PNG_VALUE, new byte[]{});
        mediaFileService.uploadFile(mockMultipartFile);
        Optional<MediaFile> file = mediaFileRepository.findByFileName("jake_peralta.png");

        Assertions.assertThat(file.isPresent()).isTrue();
        Assertions.assertThat(file.get().getFileType()).isEqualTo(FileType.IMAGE);
    }

    @Test
    @DirtiesContext
    public void uploadFile_unsupportedFileType_throwsException() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("jake_peralta.gif", "jake_peralta.png",
                MediaType.IMAGE_GIF_VALUE, new byte[]{});

        Assertions.assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> mediaFileService.uploadFile(mockMultipartFile));

        Optional<MediaFile> file = mediaFileRepository.findByFileName("jake_peralta.gif");
        Assertions.assertThat(file.isEmpty()).isTrue();
    }

    @Test
    @DirtiesContext
    public void deleteFile_givenValidId_deletesFile() throws IOException {
        Mockito.when(mockMediaFileJdbcRepository
                        .getFileReferencesForDelete(anyInt()))
                .thenReturn(new ArrayList<>());

        MediaFile file = new MediaFile("amySantiago.jpg", FileType.IMAGE, "https://testurl.com/test.jpg");
        mediaFileRepository.save(file);

        Response mockResponse = Mockito.mock(Response.class);
        Mockito.when(mockResponse.isSuccessful()).thenReturn(true);

        Mockito.when(mockFileServicesClient.deleteFile(anyString()))
                .thenReturn(mockResponse);

        mediaFileService.deleteFile(file.getMediaFileId());

        Optional<MediaFile> deletedFile = mediaFileRepository.findById(file.getMediaFileId());
        Assertions.assertThat(deletedFile.isEmpty());
    }

    @Test
    @DirtiesContext
    public void deleteFile_deleteFileCallFails_doesNotDeleteFileInDB() throws IOException {
        Mockito.when(mockMediaFileJdbcRepository
                        .getFileReferencesForDelete(anyInt()))
                .thenReturn(new ArrayList<>());

        MediaFile file = new MediaFile("captainHolt.jpg", FileType.IMAGE, "https://testurl.com/test.jpg");
        mediaFileRepository.save(file);

        Response mockResponse = Mockito.mock(Response.class);
        Mockito.when(mockResponse.isSuccessful()).thenReturn(false);

        Mockito.when(mockFileServicesClient.deleteFile(anyString()))
                .thenReturn(mockResponse);

        Assertions.assertThatExceptionOfType(IOException.class).isThrownBy(() -> mediaFileService.deleteFile(file.getMediaFileId()));

        Optional<MediaFile> undeletedFile = mediaFileRepository.findById(file.getMediaFileId());
        Assertions.assertThat(undeletedFile.isPresent()).isTrue();
    }

    @Test
    @DirtiesContext
    public void deleteFile_fileIsReferenced_throwsException() {
        MediaFile file = new MediaFile("captainHolt.jpg", FileType.IMAGE, "https://testurl.com/test.jpg");
        mediaFileRepository.save(file);

        DeleteFileReference mockDeleteFileReference = new DeleteFileReference(747, "USER");
        DeleteFileReference[] mockReferences = new DeleteFileReference[]{mockDeleteFileReference};

        Mockito.when(mockMediaFileJdbcRepository.getFileReferencesForDelete(file.getMediaFileId()))
                .thenReturn(new ArrayList<>(List.of(mockReferences)));

        Assertions.assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> mediaFileService.deleteFile(file.getMediaFileId()));
    }

}
