package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Prayer Group", description = "The Prayer Group API")
@RequestMapping("/api/v1/group")
public interface PrayerGroupApi {
    @PostMapping(value = "")
    @Operation(summary = "Creates a prayer group")
    ResponseEntity<Void> createPrayerGroup(@RequestHeader("Authorization") String authorization, @Valid @RequestBody NewPrayerGroup group);
}
