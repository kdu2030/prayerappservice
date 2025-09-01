package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.CreatePrayerGroupRequest;
import com.kevin.prayerappservice.group.models.GroupNameValidationResponse;
import com.kevin.prayerappservice.group.models.PrayerGroupModel;
import com.kevin.prayerappservice.group.models.PutPrayerGroupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PrayerGroup API", description = "The PrayerGroup API")
@RequestMapping("/api/prayergroup")
public interface PrayerGroupApi {
    @PostMapping(value = "", produces = {"application/json"}, consumes = { "application/json" })
    @Operation(summary = "Creates prayer group")
    ResponseEntity<PrayerGroupModel> createPrayerGroup(@RequestHeader("Authorization") String authorizationHeader,  @RequestBody CreatePrayerGroupRequest createPrayerGroupRequest);

    @GetMapping(value="validate-name")
    @Operation(summary = "Validates group name")
    ResponseEntity<GroupNameValidationResponse> validateGroupName(@RequestParam String name);

    @GetMapping("/{prayerGroupId}")
    @Operation(summary = "Gets prayer group")
    ResponseEntity<PrayerGroupModel> getPrayerGroup(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int prayerGroupId);

    @PutMapping("/{prayerGroupId}")
    @Operation(summary = "Updates prayer group")
    ResponseEntity<PrayerGroupModel> updatePrayerGroup(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int prayerGroupId, @RequestBody PutPrayerGroupRequest putPrayerGroupRequest);
}
