package com.example.myAirlineUserService.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Class handling all {@link ApiException}s thrown in RestControllers.
 * 
 * @since 0.0.1
 */
@ControllerAdvice
public class ApiExceptionHandler {
    
    /**
     * Handler method for any {@link ApiRequestException}.
     * 
     * @param e the apiReqeustException
     * @return a ResponseEntity with the exceptionWrapper and the status code
     */
    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiException e) {

        return new ResponseEntity<>(new ApiExceptionWrapper(ZonedDateTime.now(ZoneId.of("Z")),
                                                            e.getHttpStatus().value(),
                                                            e.getHttpStatus().getReasonPhrase(),
                                                            e.getMessage()),
                                                            e.getHttpStatus());
    }   


    /**
     * Formats an {@link ApiException}.
     *
     * @since 0.0.1
     */
    record ApiExceptionWrapper (ZonedDateTime timestamp,
                                int status,
                                String error,
                                String message) { }
}