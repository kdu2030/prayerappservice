package com.kevin.prayerappservice.controllers;

import com.kevin.prayerappservice.models.CreateUserRequest;
import com.kevin.prayerappservice.models.UserTokenPair;
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
    ResponseEntity<UserTokenPair> createUserRequest(@Valid @RequestBody CreateUserRequest createUserRequest);
}
