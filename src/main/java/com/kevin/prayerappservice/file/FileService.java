package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.file.entities.FileType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    public File uploadFile(MultipartFile rawFile){
        FileType fileType = FileType.getFileTypeFromContentType(rawFile.getContentType());
        if(fileType == FileType.UNKNOWN){
            throw new DataValidationException(new String[] {"File type is not supported."});
        }

        return null;
    }

}
