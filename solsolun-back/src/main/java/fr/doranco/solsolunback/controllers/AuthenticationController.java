package fr.doranco.solsolunback.controllers;

import fr.doranco.solsolunback.dto.authentication.LoginRequest;
import fr.doranco.solsolunback.dto.authentication.RegisterRequest;
import fr.doranco.solsolunback.services.interfaces.IAuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(
            @Validated @RequestBody RegisterRequest request
    ) {
        System.out.println(request);

        return authenticationService.register(request);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(
            @Validated @RequestBody LoginRequest request
    ) {
        return authenticationService.login(request);
    }
}
