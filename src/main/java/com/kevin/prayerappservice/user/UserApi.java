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
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "the User API")
@RequestMapping("/api/user")
public interface UserApi {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @Operation(
            operationId = "createUser",
            summary = "Creates a user",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item added successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    UserSummary.class))
                    })
            }
    )
    ResponseEntity<UserSummary> createUser(@Valid @RequestBody CreateUserRequest createUserRequest);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/summary",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @Operation(
            operationId = "getUserSummary",
            summary = "Gets user summary from user credentials",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "User summary fetched successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    UserSummary.class))
                    }),
            }
    )
    ResponseEntity<UserSummary> getUserSummary(@Valid @RequestBody UserCredentials userCredentials);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/token",
            produces = {"application/json"}
    )
    @Operation(
            operationId = "getUserTokenPair",
            summary = "Gets a new user token pair from a refresh token",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "User token pair generated successfully",
                            content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    UserTokenPair.class))
                    })
            }
    )
    ResponseEntity<UserTokenPair> getUserTokenPair(@RequestHeader("Authorization") String authorization);

    @GetMapping("/{userId}/summary")
    @Operation(summary = "Gets a user summary from a user ID")
    ResponseEntity<UserSummary> getUserSummary(int userId);
}
