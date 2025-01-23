package fr.doranco.solsolunback.exceptions.alreadyExists;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
