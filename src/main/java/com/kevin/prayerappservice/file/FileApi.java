package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.entities.File;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File", description = "The File API")
@RequestMapping("/api/v1/file")
public interface FileApi {
    @PostMapping(value = "", produces = {"application/json"}, consumes = { "multipart/form-data" })
    @Operation(summary = "Uploads file")
    ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file);

   @DeleteMapping(value = "/{fileId}")
   @Operation(summary = "Deletes file")
   ResponseEntity<Void> deleteFile(@PathVariable int fileId);

}
