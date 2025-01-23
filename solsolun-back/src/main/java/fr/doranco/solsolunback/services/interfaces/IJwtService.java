package fr.doranco.solsolunback.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public interface IJwtService {
    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    boolean validateToken(String token, UserDetails userDetails);

    String getUsernameFromToken(String token);

    Double getBalanceFromToken(String token);

    Date getExpirationDateFromToken(String token);

    boolean isTokenExpired(String token);
}
