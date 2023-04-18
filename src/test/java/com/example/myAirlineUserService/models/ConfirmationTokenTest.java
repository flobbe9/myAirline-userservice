package com.example.myAirlineUserService.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test class for {@link ConfirmationToken}.
 * 
 * @since 0.0.2
 */
public class ConfirmationTokenTest {

    private ConfirmationToken token;


    @BeforeEach
    void setUp() {

        this.token = new ConfirmationToken(UUID.randomUUID().toString(), 
                                            "someEmail@gmail.com", 
                                            LocalDateTime.now(), 
                                            LocalDateTime.now().plusMinutes(15), 
                                            null);
    }


    @Test
    void isExpired_shouldBeFalse() {

        assertFalse(token.isExpired());
    }


    @Test
    void isExpired_shouldBeTrue() {

        // set expiresAt to 1 minute in the past
        token.setExpiresAt(LocalDateTime.now().minusMinutes(1));

        assertTrue(token.isExpired());
    }
}