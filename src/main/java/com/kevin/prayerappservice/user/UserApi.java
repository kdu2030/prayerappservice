package com.kevin.prayerappservice.user;

import com.kevin.prayerappservice.user.models.CreateUserRequest;
import com.kevin.prayerappservice.user.models.UserCredentials;
import com.kevin.prayerappservice.user.models.UserSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Tag(name = "User", description = "the User API")
@RequestMapping("/api/v1/user")
public interface UserApi {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    @Operation(
            operationId = "createUser",
            summary = "Creates a user",
            tags = { "User" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item added successfully", content = {
                            @Content(mediaType = "application/json", schema=@Schema(implementation = UserSummary.class))
                    })
            }
    )
    ResponseEntity<UserSummary> createUser(@Valid @RequestBody CreateUserRequest createUserRequest);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/summary",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    @Operation(
            operationId = "getUserSummary",
            summary = "Gets user summary from user credentials",
            tags = { "User" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User summary fetched successfully", content = {
                            @Content(mediaType = "application/json", schema=@Schema(implementation = UserSummary.class))
                    }),
            }
    )
    ResponseEntity<UserSummary> getUserSummary(@Valid @RequestBody UserCredentials userCredentials);
}
