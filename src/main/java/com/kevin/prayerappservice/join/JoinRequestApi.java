package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.join.models.JoinRequestCreateRequest;
import com.kevin.prayerappservice.join.models.JoinRequestModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "JoinRequest API", description = "The JoinRequest API")
public interface JoinRequestApi {
    @PostMapping("/api/prayergroup/{prayerGroupId}/joinrequest")
    @Operation(summary = "Create a prayer group join request")
    ResponseEntity<JoinRequestModel> createJoinRequest(@PathVariable int prayerGroupId, @RequestBody JoinRequestCreateRequest createRequest);
}
