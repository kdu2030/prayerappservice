package com.kevin.prayerappservice.user;

import com.kevin.prayerappservice.user.models.CreateUserRequest;
import com.kevin.prayerappservice.user.models.UserCredentials;
import com.kevin.prayerappservice.user.models.UserSummary;
import com.kevin.prayerappservice.user.models.UserTokenPair;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Tag(name = "User", description = "the User API")
@RequestMapping("/api/v1/user")
public interface UserApi {

    @RequestMapping(method = RequestMethod.POST, value = "", produces = {"application/json"}, consumes = {
            "application/json"})
    @Operation(summary = "Creates a user")
    ResponseEntity<UserSummary> createUser(@Valid @RequestBody CreateUserRequest createUserRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/summary", produces = {"application/json"}, consumes = {
            "application/json"})
    @Operation(summary = "Gets user summary from user credentials")
    ResponseEntity<UserSummary> getUserSummary(@Valid @RequestBody UserCredentials userCredentials);

    @RequestMapping(method = RequestMethod.GET, value = "/token", produces = {"application/json"})
    @Operation(summary = "Gets a new user token pair from a refresh token")
    ResponseEntity<UserTokenPair> getUserTokenPair(@RequestHeader("Authorization") String authorization);
}
