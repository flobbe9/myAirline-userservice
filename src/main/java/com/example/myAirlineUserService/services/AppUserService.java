package com.example.myAirlineUserService.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.myAirlineUserService.models.AppUser;
import com.example.myAirlineUserService.models.ConfirmationToken;
import com.example.myAirlineUserService.repositories.AppUserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


/**
 * Class with logic related to {@link AppUser}.
 * 
 * @since 0.0.2
 */
@Service
@Validated
public class AppUserService implements UserDetailsService {
    
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String CONFIRMATION_EMAIL_PATH = "./resources/ConfirmationEmail.html";

    
    /**
     * Get user by email or throw an exception.
     * 
     * @param email of the user
     * @return the user owning the email
     * @throws IllegalStateException if email is not found
     */
    public AppUser getByEmail(@NotBlank String email) {

        return appUserRepository.findByEmail(email).orElseThrow(() ->
            new IllegalStateException("Could not find user with email: " + email + "."));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return getByEmail(username);
    }


    public AppUser save(@Valid AppUser appUser) {

        return appUserRepository.save(appUser);
    }


    public AppUser register(@Valid AppUser appUser) {

        isRegistrationValid(appUser);

        sendConfirmationEmail(appUser);

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        return appUserRepository.save(appUser);
    }


    public AppUser confirmEmail(@NotBlank String token) {

        // handle confirmation token
        ConfirmationToken confirmationToken = confirmationTokenService.confirmToken(token);

        // user should exist
        AppUser appUser = getByEmail(confirmationToken.getUserEmail());

        // enable user
        appUser.setEnabled(true);

        return save(appUser);
    }


    /**
     * Checks user that will be registered.
     * 
     * @param appUser to be checkd
     * @return true if user is valid
     * @throws IllegalStateException if user is invalid
     */
    private boolean isRegistrationValid(@Valid AppUser appUser) {

        String email = appUser.getEmail();

        // email should not exist
        if (appUserRepository.findByEmail(email).isPresent())
            throw new IllegalStateException("User with email: " + email + " does already exist.");

        return true;
    }


    private String sendConfirmationEmail(@Valid AppUser appUser) {

        ConfirmationToken token = new ConfirmationToken(UUID.randomUUID().toString(), 
                                                        appUser.getEmail(), 
                                                        LocalDateTime.now(), 
                                                        LocalDateTime.now().plusMinutes(15), 
                                                        null);

        confirmationTokenService.save(token);

        String content = htmlToString(CONFIRMATION_EMAIL_PATH)
                         .formatted(appUser.getFirstName(), token.getToken());

        emailService.sendEmail(appUser.getEmail(), 
                               content,
                               "myAirline | Confirm your account", 
                               null);

        return token.getToken();
    }


    private String htmlToString(@NotBlank String filePath) {

        try (InputStream fos = getClass().getResourceAsStream(filePath);
             Reader ir = new InputStreamReader(fos);
             BufferedReader br = new BufferedReader(ir)) {
                
            return br.lines().collect(Collectors.joining());

        } catch (IOException e) {
            throw new IllegalStateException("Failed to read html file at path: " + filePath + ".");
        }
    }
}