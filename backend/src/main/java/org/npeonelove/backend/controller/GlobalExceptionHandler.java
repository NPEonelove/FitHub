package org.npeonelove.backend.controller;

import org.apache.tomcat.websocket.AuthenticationException;
import org.npeonelove.backend.exception.ErrorResponse;
import org.npeonelove.backend.exception.auth.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final HttpStatus authenticationStatus = HttpStatus.UNAUTHORIZED;
    private final HttpStatus usernameNotFoundStatus = HttpStatus.NOT_FOUND;
    private final HttpStatus userNotFoundStatus = HttpStatus.NOT_FOUND;
    private final HttpStatus userValidationStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus jwtValidationStatus = HttpStatus.FORBIDDEN;
    private final HttpStatus httpMessageNotReadableStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus passwordChangeStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus emailNotUniqueStatus = HttpStatus.CONFLICT;
    private final HttpStatus signOutStatus = HttpStatus.CONFLICT;
    private final HttpStatus httpMediaTypeNotSupportedStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex) {
        return ResponseEntity.status(authenticationStatus).body(
                new ErrorResponse(
                        authenticationStatus.value(),
                        "Auth exception",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(usernameNotFoundStatus).body(
                new ErrorResponse(
                        usernameNotFoundStatus.value(),
                        "User not found",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(userNotFoundStatus).body(
                new ErrorResponse(
                        userNotFoundStatus.value(),
                        "User not found",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserValidation(UserValidationException ex) {
        return ResponseEntity.status(userValidationStatus).body(
                new ErrorResponse(
                        userValidationStatus.value(),
                        "User Validation Exception",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ErrorResponse> handleJwtValidation(JwtValidationException ex) {
        return ResponseEntity.status(jwtValidationStatus).body(
                new ErrorResponse(
                        jwtValidationStatus.value(),
                        "Jwt Validation Exception",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(httpMessageNotReadableStatus).body(
                new ErrorResponse(
                        httpMessageNotReadableStatus.value(),
                        "Incorrect JSON format",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(PasswordChangeException.class)
    public ResponseEntity<ErrorResponse> handlePasswordChange(PasswordChangeException ex) {
        return ResponseEntity.status(passwordChangeStatus).body(
                new ErrorResponse(
                        passwordChangeStatus.value(),
                        "Password change Exception",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotUnique(EmailNotUniqueException ex) {
        return ResponseEntity.status(emailNotUniqueStatus).body(
                new ErrorResponse(
                        emailNotUniqueStatus.value(),
                        "Email already exists",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(SignOutException.class)
    public ResponseEntity<ErrorResponse> handleSignOut(SignOutException ex) {
        return ResponseEntity.status(signOutStatus).body(
                new ErrorResponse(
                        signOutStatus.value(),
                        "Sign Out Exception",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(httpMediaTypeNotSupportedStatus).body(
                new ErrorResponse(
                        httpMediaTypeNotSupportedStatus.value(),
                        "Unsupported Media Type",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

}
