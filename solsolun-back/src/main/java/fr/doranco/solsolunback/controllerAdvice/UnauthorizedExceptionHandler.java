package fr.doranco.solsolunback.controllerAdvice;

import fr.doranco.solsolunback.exceptions.invalid.InvalidCredentialsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class UnauthorizedExceptionHandler {
    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> invalidCredentials(InvalidCredentialsException exception, WebRequest request) {
        return ResponseEntity.status(UNAUTHORIZED)
                .body(exception.getMessage());
    }
}
