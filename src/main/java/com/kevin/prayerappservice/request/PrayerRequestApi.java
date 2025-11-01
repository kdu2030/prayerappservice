package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.models.PrayerRequestCreateRequest;
import com.kevin.prayerappservice.request.models.PrayerRequestFilterCriteria;
import com.kevin.prayerappservice.request.models.PrayerRequestGetResponse;
import com.kevin.prayerappservice.request.models.PrayerRequestModel;
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
}
