package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.join.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "JoinRequest API", description = "The JoinRequest API")
public interface JoinRequestApi {
    @PostMapping("/api/prayergroup/{prayerGroupId}/joinrequest")
    @Operation(summary = "Create a prayer group join request")
    ResponseEntity<JoinRequestModel> createJoinRequest(@PathVariable int prayerGroupId, @RequestBody JoinRequestCreateRequest createRequest);

    @PostMapping("/api/prayergroup/{prayerGroupId}/joinrequests/search")
    @Operation(summary = "Get prayer group join requests")
    ResponseEntity<JoinRequestsGetResponse> getJoinRequests(@PathVariable int prayerGroupId, @RequestBody JoinRequestsGetRequest getRequest);

    @DeleteMapping("/api/prayergroup/{prayerGroupId}/joinrequests")
    @Operation(summary = "Delete join requests")
    ResponseEntity<Void> deleteJoinRequests(@RequestHeader("Authorization") String authToken, @PathVariable int prayerGroupId, @RequestBody JoinRequestDeleteRequest deleteRequest);

    @PostMapping("/api/prayergroup/{prayerGroupId}/joinrequests")
    @Operation(summary = "Approve prayer group join requests")
    ResponseEntity<Void> approveJoinRequests(@RequestHeader("Authorization") String authToken, @PathVariable int prayerGroupId, @RequestBody JoinRequestApproveRequest approveRequest);
}
