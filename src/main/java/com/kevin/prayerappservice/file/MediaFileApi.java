package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.models.MediaFileSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "MediaFile", description = "The Media File API")
@RequestMapping("/api/file")
public interface MediaFileApi {
    @PostMapping(value = "", produces = {"application/json"}, consumes = { "multipart/form-data" })
    @Operation(summary = "Uploads file")
    ResponseEntity<MediaFileSummary> uploadFile(@RequestParam("file") MultipartFile file) throws IOException;

   @DeleteMapping(value = "/{fileId}")
   @Operation(summary = "Deletes file")
   ResponseEntity<Void> deleteFile(@PathVariable int fileId) throws IOException;

}
