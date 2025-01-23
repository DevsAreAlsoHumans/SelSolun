package fr.doranco.solsolunback.services.interfaces;

import fr.doranco.solsolunback.dto.authentication.LoginRequest;
import fr.doranco.solsolunback.dto.authentication.RegisterRequest;

import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {
    ResponseEntity<String> register(RegisterRequest request);

    ResponseEntity<String> login(LoginRequest request);
}
