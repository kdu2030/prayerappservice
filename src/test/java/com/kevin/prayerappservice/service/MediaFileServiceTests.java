package com.kevin.prayerappservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.FileRepository;
import com.kevin.prayerappservice.file.FileService;
import com.kevin.prayerappservice.file.FileServicesClient;
import com.kevin.prayerappservice.file.dtos.FileUploadResponse;
import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.file.entities.FileType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@ActiveProfiles("test")
public class MediaFileServiceTests {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileServicesClient mockFileServicesClient;

    @Autowired
    private FileService fileService;

    @Test
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
        fileService.uploadFile(mockMultipartFile);
        Optional<MediaFile> file = fileRepository.findByFileName("jake_peralta.png");

        Assertions.assertThat(file.isPresent()).isTrue();
        Assertions.assertThat(file.get().getFileType()).isEqualTo(FileType.IMAGE);
    }

    @Test
    public void uploadFile_unsupportedFileType_throwsException() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("jake_peralta.gif", "jake_peralta.png",
                MediaType.IMAGE_GIF_VALUE, new byte[]{});

        Assertions.assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> fileService.uploadFile(mockMultipartFile));

        Optional<MediaFile> file = fileRepository.findByFileName("jake_peralta.gif");
        Assertions.assertThat(file.isEmpty()).isTrue();
    }

    @Test
    public void deleteFile_givenValidId_deletesFile(){

    }
}
