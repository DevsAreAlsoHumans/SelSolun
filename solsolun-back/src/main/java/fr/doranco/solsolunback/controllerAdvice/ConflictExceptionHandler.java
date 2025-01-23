package fr.doranco.solsolunback.controllerAdvice;

import fr.doranco.solsolunback.exceptions.alreadyExists.UserAlreadyExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ConflictExceptionHandler {
    private static final HttpStatus CONFLICT = HttpStatus.CONFLICT;

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> userAlreadyExists(UserAlreadyExistsException exception, WebRequest request) {
        return ResponseEntity.status(CONFLICT)
                .body(exception.getMessage());
    }
}
