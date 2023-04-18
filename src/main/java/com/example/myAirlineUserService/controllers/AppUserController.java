package com.example.myAirlineUserService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
public class AppUserController {
    
    @Autowired
    private AppUserService appUserService;


    @GetMapping("/getByEmail")
    public AppUser getByEmail(@RequestParam @NotBlank(message = "Email cannot be blank.") String email) {

        return appUserService.getByEmail(email);
    }


    @PostMapping("/register")
    public AppUser register(@RequestBody @Valid AppUser appUser) {

        if (appUser.getRole() == null)
            appUser.setRole(AppUserRole.USER);

        return appUserService.register(appUser);
    }


    @GetMapping("/confirmEmail")
    @ResponseStatus(code = HttpStatus.OK, reason = "Email confirmed.")
    public void confirmEmail(@RequestParam @NotBlank(message = "Confirmation token cannot be blank.") String token) {

        appUserService.confirmEmail(token);
    }
}