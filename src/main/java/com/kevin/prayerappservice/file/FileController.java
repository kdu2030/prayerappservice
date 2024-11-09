package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.entities.File;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController implements FileApi{
    @Override
    public ResponseEntity<File> uploadFile(MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteFile(@PathVariable int fileId){
        return null;
    }
}
