package com.example.myAirlineUserService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.myAirlineUserService.models.AppUser;
import com.example.myAirlineUserService.models.AppUserRole;
import com.example.myAirlineUserService.services.AppUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


/**
 * Class handling all endpoints related to {@link AppUser}.
 * 
 * @since 0.0.2
 */
@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class AppUserController {
    
    @Autowired
    private AppUserService appUserService;


    @GetMapping("/getByEmail")
    @Operation(summary = "Get user by email.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found user.", content = {@Content(mediaType = "application/json")}),
        // @ApiResponse(responseCode = "400", description = "Invalid email.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "User with this email not found.", content = {@Content(mediaType = "application/json")}),
    })
    public AppUser getByEmail(@RequestParam @NotBlank(message = "Email cannot be blank.") String email) {

        return appUserService.getByEmail(email);
    }


    @PostMapping("/register")
    @Operation(summary = "Save and validate new user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registered user.", content = {@Content(mediaType = "application/json")}),
        // @ApiResponse(responseCode = "400", description = "Invalid email.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "User does already exist.", content = {@Content(mediaType = "application/json")}),
    })
    public AppUser register(@RequestBody @Valid AppUser appUser) {

        if (appUser.getRole() == null)
            appUser.setRole(AppUserRole.USER);

        return appUserService.register(appUser);
    }


    @GetMapping("/confirmEmail")
    @ResponseStatus(code = HttpStatus.OK, reason = "Email confirmed.")
    @Operation(summary = "Confirm a users email.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email confirmed.", content = {@Content(mediaType = "application/json")}),
        // @ApiResponse(responseCode = "400", description = "Invalid email.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "Token invliad.", content = {@Content(mediaType = "application/json")}),
    })
    public void confirmEmail(@RequestParam @NotBlank(message = "Confirmation token cannot be blank.") String token) {

        appUserService.confirmEmail(token);
    }
}