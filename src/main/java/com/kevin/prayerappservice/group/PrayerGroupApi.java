package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import com.kevin.prayerappservice.group.models.PrayerGroupSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Prayer Group", description = "The Prayer Group API")
@RequestMapping("/api/v1/group")
public interface PrayerGroupApi {
    @PostMapping(value = "")
    @Operation(summary = "Creates a prayer group")
    ResponseEntity<PrayerGroupSummary> createPrayerGroup(@RequestHeader("Authorization") String authorization,
                                                         @Valid @RequestBody NewPrayerGroup group);

    @GetMapping(value = "/{prayerGroupId}", produces = {"application/json"})
    @Operation(summary = "Gets a prayer group summary")
    ResponseEntity<PrayerGroupSummary> getPrayerGroup(@PathVariable int prayerGroupId);


}
