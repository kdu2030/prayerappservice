package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.models.MediaFileSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MediaMediaFileController implements MediaFileApi {
    private final MediaFileService mediaFileService;

    @Autowired
    public MediaMediaFileController(MediaFileService mediaFileService){
        this.mediaFileService = mediaFileService;
    }

    @Override
    public ResponseEntity<MediaFileSummary> uploadFile(MultipartFile rawFile) throws IOException {
        MediaFileSummary file = mediaFileService.uploadFile(rawFile);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteFile(@PathVariable int fileId) throws IOException{
        mediaFileService.deleteFile(fileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
