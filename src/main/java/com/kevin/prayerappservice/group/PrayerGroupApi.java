package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

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

    @PostMapping("/{prayerGroupId}/user/{userId}")
    @Operation(summary = "Adds prayer group user")
    ResponseEntity<PrayerGroupUserModel> addPrayerGroupUser(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int prayerGroupId, @PathVariable int userId);

    @DeleteMapping("/{prayerGroupId}/user/{userId}")
    @Operation(summary = "Removes a prayer group user")
    ResponseEntity<Void> deletePrayerGroupUser(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int prayerGroupId, @PathVariable int userId);

    @PostMapping("/{prayerGroupId}/users")
    @Operation(summary = "Gets prayer group users")
    ResponseEntity<PrayerGroupUsersGetResponse> getPrayerGroupUsers(@PathVariable int prayerGroupId, @RequestBody PrayerGroupUsersGetRequest request);

    @PutMapping("/{prayerGroupId}/users")
    @Operation(summary = "Updates prayer group users")
    ResponseEntity<Void> updatePrayerGroupUsers(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int prayerGroupId, @RequestBody PrayerGroupUserUpdateRequest prayerGroupUserUpdateRequest) throws SQLException;
}
