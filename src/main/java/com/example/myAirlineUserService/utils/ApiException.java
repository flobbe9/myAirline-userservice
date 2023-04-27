package com.example.myAirlineUserService.utils;

import org.springframework.http.HttpStatus;

import lombok.Getter;


/**
 * Class defining a custom exception that holds the http status in addition
 * to the error message.
 * 
 * @since 0.0.1
 * @see ApiExceptionHandler
 */
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;


    /**
     * Constructor extending a normal exception with an http status.
     * 
     * @param message the error message.
     * @param httpStatus of the exception.
     */
    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}