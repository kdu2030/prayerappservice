package com.kevin.prayerappservice.controllers;

import com.kevin.prayerappservice.models.CreateUserRequest;
import com.kevin.prayerappservice.models.UserTokenPair;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
public interface UserApi {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/v1/user",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    @Operation(
            operationId = "createUser",
            summary = "Creates a user",
            tags = { "User" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item added successfully", content = {
                            @Content(mediaType = "application/json", schema=@Schema(implementation = UserTokenPair.class))
                    })
            }
    )
    ResponseEntity<UserTokenPair> createUser(@Valid @RequestBody CreateUserRequest createUserRequest);
}
