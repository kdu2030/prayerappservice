package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PrayerRequest API", description = "The PrayerRequest API")
@RequestMapping("/api/prayerrequest")
public interface PrayerRequestApi {
    @PostMapping
    @Operation(summary = "Creates a prayer request")
    ResponseEntity<PrayerRequestModel> createPrayerRequest(@RequestHeader("Authorization") String authToken, @Valid @RequestBody PrayerRequestCreateRequest prayerRequestCreateRequest);

    @PostMapping("/filter")
    @Operation(summary =  "Gets prayer requests")
    ResponseEntity<PrayerRequestGetResponse> getPrayerRequests(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody PrayerRequestFilterCriteria prayerRequestFilterCriteria);

    @PostMapping("/{prayerRequestId}/like")
    @Operation(summary = "Creates a prayer request like")
    ResponseEntity<PrayerRequestLikeModel> createPrayerRequestLike(@PathVariable int prayerRequestId, @Valid @RequestBody PrayerRequestActionCreateRequest createRequest);

    @DeleteMapping("/like/{prayerRequestLikeId}")
    @Operation(summary = "Deletes a prayer request like")
    ResponseEntity<Void> deletePrayerRequestLike( @RequestHeader("Authorization") String authHeader, @PathVariable int prayerRequestLikeId);

    @PostMapping("/{prayerRequestId}/bookmark")
    @Operation(summary = "Creates a prayer request bookmark")
    ResponseEntity<PrayerRequestBookmarkModel> createPrayerRequestBookmark(@PathVariable int prayerRequestId, @Valid @RequestBody PrayerRequestActionCreateRequest createRequest);

    @DeleteMapping("/bookmark/{prayerRequestBookmarkId}")
    @Operation(summary = "Deletes a prayer request bookmark")
    ResponseEntity<Void> deletePrayerRequestBookmark(@RequestHeader("Authorization") String authHeader, @PathVariable int prayerRequestBookmarkId);
}
