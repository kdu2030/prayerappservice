package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.CreatePrayerGroupRequest;
import com.kevin.prayerappservice.group.models.PrayerGroupModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "PrayerGroup API", description = "The PrayerGroup API")
@RequestMapping("/api/prayergroup")
public interface PrayerGroupApi {
    @PostMapping(value = "", produces = {"application/json"}, consumes = { "application/json" })
    @Operation(summary = "Creates prayer group")
    ResponseEntity<PrayerGroupModel> createPrayerGroup(@RequestHeader("Authorization") String authorizationHeader,  @RequestBody CreatePrayerGroupRequest createPrayerGroupRequest);
}
