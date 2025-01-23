package fr.doranco.solsolunback.controllers;

import fr.doranco.solsolunback.services.interfaces.IJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final IJwtService jwtService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            return ResponseEntity.ok(jwtService.isTokenExpired(authHeader.substring(7)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestHeader("Authorization") String authHeader) {
        try {
            return ResponseEntity.ok(jwtService.getBalanceFromToken(authHeader.substring(7)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
