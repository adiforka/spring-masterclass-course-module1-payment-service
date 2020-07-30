package com.adison.shop.common.web;

import com.adison.shop.users.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> onException(Exception ex, Locale locale) {
        ex.printStackTrace();
        return createResponse(ex, INTERNAL_SERVER_ERROR, locale);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> onUserNotFoundException(UserNotFoundException ex, Locale locale) {
        return createResponse(ex, NOT_FOUND, locale);
    }

    private ResponseEntity<ExceptionDTO> createResponse(Exception ex, HttpStatus status, Locale locale) {
        var exceptionName = ex.getClass().getSimpleName();
        String description;
        try {
            //message is under the key of the simple name of the class of the exception handling the sit
            description = messageSource.getMessage(ex.getClass().getSimpleName(), null, locale);
        } catch (NoSuchMessageException exception) {
            description = exceptionName;
        }
        ex.printStackTrace();
        return ResponseEntity.status(status).body(new ExceptionDTO(description));
    }
}
