package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.models.FileSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController implements FileApi{
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<FileSummary> uploadFile(MultipartFile rawFile) throws IOException {
        FileSummary file = fileService.uploadFile(rawFile);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteFile(@PathVariable int fileId) throws IOException{
        fileService.deleteFile(fileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
