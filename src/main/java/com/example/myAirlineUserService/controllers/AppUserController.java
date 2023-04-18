package com.example.myAirlineUserService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myAirlineUserService.models.AppUser;
import com.example.myAirlineUserService.services.AppUserService;

import jakarta.validation.constraints.NotBlank;


/**
 * Class handling all endpoints related to {@link AppUser}.
 * 
 * @since 0.0.2
 */
@RestController
@Validated
public class AppUserController {
    
    @Autowired
    private AppUserService appUserService;


    @GetMapping("/getByEmail")
    public AppUser getByEmail(@RequestParam @NotBlank(message = "Email cannot be blank.") String email) {
        
        return appUserService.getByEmail(email);
    }
}