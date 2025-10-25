package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.models.PrayerRequestCreateRequest;
import com.kevin.prayerappservice.request.models.PrayerRequestModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "PrayerRequest API", description = "The PrayerRequest API")
public interface PrayerRequestApi {
    @PostMapping("/api/prayergroup/{prayerGroupId}/prayerrequest")
    @Operation(summary = "Creates a prayer request")
    ResponseEntity<PrayerRequestModel> createPrayerRequest(@RequestHeader("Authorization") String authToken, @PathVariable int prayerGroupId, @Valid @RequestBody PrayerRequestCreateRequest prayerRequestCreateRequest);
}
