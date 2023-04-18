package com.example.myAirlineUserService.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myAirlineUserService.models.ConfirmationToken;
import com.example.myAirlineUserService.repositories.ConfirmationTokenRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


/**
 * Class handling logic related to {@link ConfirmationToken}.
 * 
 * @since 0.0.2
 */
@Service
public class ConfirmationTokenService {
    
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;


    public ConfirmationToken getByToken(@NotBlank String token) {

        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> 
            new IllegalStateException("Could not find confirmation token: " + token + "."));
    }


    public ConfirmationToken getByUserEmail(@NotBlank String userEmail) {

        return confirmationTokenRepository.findByToken(userEmail).orElseThrow(() -> 
            new IllegalStateException("Could not find confirmation token with user email: " + userEmail + "."));
    }


    public ConfirmationToken save(@Valid ConfirmationToken token) {

        return confirmationTokenRepository.save(token);
    }


    public boolean exists(@NotBlank String token) {

        return confirmationTokenRepository.existsByToken(token);
    }


    public boolean canTokenBeConfirmed(@NotBlank ConfirmationToken token) {

        // should exist
        if (!exists(token.getToken()))
            return false;

        // should not be confirmed yet
        if (token.getConfirmedAt() != null)
            return false;

        // should not be expired yet
        if (token.isExpired())
            return false;

        return true;
    }


    public ConfirmationToken confirmToken(@NotBlank String token) {

        ConfirmationToken confirmationToken = getByToken(token);

        // token should be valid
        if (!canTokenBeConfirmed(confirmationToken)) 
            throw new IllegalStateException("Failed to confirm account. Token invalid.");  

        // set confirmedAt
        confirmationToken.setConfirmedAt(LocalDateTime.now());

        // save token
        return confirmationTokenRepository.save(confirmationToken);
    }
}