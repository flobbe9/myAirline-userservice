package com.example.myAirlineUserService.models.validation;

import org.springframework.validation.annotation.Validated;

import com.example.myAirlineUserService.models.AppUser;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


/**
 * Class with validation logic for {@link AppUser}.
 * 
 * @since 0.0.2
 */
@Validated
public class AppUserValidator implements ConstraintValidator<ValidAppUser, AppUser> {

    @Override
    public boolean isValid(AppUser value, ConstraintValidatorContext context) {

        // disable default error message
        context.disableDefaultConstraintViolation();

        // TODO: call other validation methods

        return true;
    }
    
}