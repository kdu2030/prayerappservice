package com.kevin.prayerappservice.user;

import com.kevin.prayerappservice.user.models.CreateUserRequest;
import com.kevin.prayerappservice.user.models.UserCredentials;
import com.kevin.prayerappservice.user.models.UserSummary;
import com.kevin.prayerappservice.user.models.UserTokenPair;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "The User API")
@RequestMapping("/api/v1/user")
public interface UserApi {

    @PostMapping(value = "", produces = {"application/json"}, consumes = {
            "application/json"})
    @Operation(summary = "Creates a user")
    ResponseEntity<UserSummary> createUser(@Valid @RequestBody CreateUserRequest createUserRequest);

    @PostMapping(value = "/summary", produces = {"application/json"}, consumes = {
            "application/json"})
    @Operation(summary = "Gets user summary from user credentials")
    ResponseEntity<UserSummary> getUserSummary(@Valid @RequestBody UserCredentials userCredentials);

    @GetMapping(value = "/token", produces = {"application/json"})
    @Operation(summary = "Gets a new user token pair from a refresh token")
    ResponseEntity<UserTokenPair> getUserTokenPair(@RequestHeader("Authorization") String authorization);
}
