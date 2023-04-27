package com.example.myAirlineUserService.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.myAirlineUserService.models.ConfirmationToken;
import com.example.myAirlineUserService.repositories.ConfirmationTokenRepository;
import com.example.myAirlineUserService.utils.LambdaReturn;
import com.example.myAirlineUserService.utils.LambdaVoid;

import jakarta.validation.ConstraintViolationException;


/**
 * Test class for {@link ConfirmationTokenService}.
 * 
 * @since 0.0.1
 */
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class ConfirmationTokenServiceTest {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    private final String randomToken = UUID.randomUUID().toString();

    private String musterEmail = "musteremail@domain.com";

    private ConfirmationToken token;


    @BeforeAll
    void setupBeforeAll() {

        confirmationTokenRepository.save(new ConfirmationToken(randomToken, 
                                        musterEmail, 
                                        LocalDateTime.now(), 
                                        LocalDateTime.now().plusMinutes(15), 
                                        null));

        // should have saved token
        assertEquals(1, confirmationTokenRepository.findAll().size());
    }


    @BeforeEach
    void setup() {

        this.token = new ConfirmationToken(randomToken, 
                                           musterEmail, 
                                           LocalDateTime.now(), 
                                           LocalDateTime.now().plusMinutes(15), 
                                           null);
    }


    @Test
    void getByToken_shouldThrowNotFound() {

        String nonExistingToken = "nonExistingToken";
        
        // should throw exception
        assertThrows(IllegalStateException.class, () -> confirmationTokenService.getByToken(nonExistingToken));
        
        // should be correct cause
        String errorMessage = getErrorMessage(() -> confirmationTokenService.getByToken(nonExistingToken), null);
        assertEquals("Could not find confirmation token: " + nonExistingToken + ".", errorMessage);
    }


    @Test
    void getByToken_shouldFindToken() {

        assertEquals(token, confirmationTokenService.getByToken(randomToken));
    }


    @Test
    void getByUserEmail_shouldThrowNotFound() {

        String nonExistingEmail = "nonExistingEmail@domain.com";

        // should throw exception
        assertThrows(IllegalStateException.class, () -> confirmationTokenService.getByUserEmail(nonExistingEmail));

        // should be correct cause
        String errorMessage = getErrorMessage(() -> confirmationTokenService.getByUserEmail(nonExistingEmail), null);
        assertEquals("Could not find confirmation token with user email: " + nonExistingEmail + ".", errorMessage);
    }


    @Test
    void getByUserEmail_shouldFindToken() {

        assertEquals(token, confirmationTokenService.getByUserEmail(musterEmail));
    }


    @Test 
    void save_shouldThrowInvalidAppUser() {

        // should throw exception
        token.setToken(null);
        assertThrows(ConstraintViolationException.class, () -> confirmationTokenService.save(token));

        token = null;
        assertThrows(ConstraintViolationException.class, () -> confirmationTokenService.save(token));
    }


    @Test
    void confirmToken() {


    }


    @Test
    void testExists() {

    }


    /**
     * Takes a {@link ThrowErrorLambda} function that is expected to throw an 
     * {@link IllegalStateException} and returns the error message.
     * <p>
     * If either arguemnt is null, the function wont be called.
     * 
     * @param lambdaReturn function that should throw an IllegalStateException
     * @param lambdaVoid function that should throw an IllegalStateException
     * @return the error message or null if no exception was thrown
     */
    private String getErrorMessage(LambdaReturn lambdaReturn, LambdaVoid lambdaVoid) {

        try {
            if (lambdaReturn != null)
                lambdaReturn.returnsSomething();

            if (lambdaVoid != null)
                lambdaVoid.returnsNothing();


        } catch (IllegalStateException e) {
            return e.getMessage();
        }

        return null;
    }
}