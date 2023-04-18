package com.example.myAirlineUserService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myAirlineUserService.models.AppUser;
import com.example.myAirlineUserService.repositories.AppUserRepository;

import jakarta.validation.constraints.NotBlank;


/**
 * Class with logic related to {@link AppUser}.
 * 
 * @since 0.0.2
 */
@Service
public class AppUserService {
    
    @Autowired
    private AppUserRepository appUserRepository;

    
    public AppUser getByEmail(@NotBlank String email) {

        return appUserRepository.findByEmail(email).orElseThrow(() ->
            new IllegalStateException("Could not find user with email: " + email + "."));
    }
}