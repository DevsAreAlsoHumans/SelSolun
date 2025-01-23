package fr.doranco.solsolunback.services;

import fr.doranco.solsolunback.entities.User;
import fr.doranco.solsolunback.repositories.UserRepository;
import fr.doranco.solsolunback.services.interfaces.IJwtService;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class JwtService implements IJwtService {
    private static final String USER = "User";

    private final UserRepository userRepository;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildToken(
            Map<String, Object> claims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    private <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(
            String token
    ) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new RuntimeException("Token invalide ou expiré");
        }
    }

    @Override
    public String generateToken(
            UserDetails userDetails
    ) {
        final Map<String, Object> claims = new HashMap<>();
        final User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec email : " + userDetails.getUsername()));
        claims.put("balance", user.getBalance() != null ? user.getBalance() : 0.0);

        return generateToken(claims, userDetails);
    }

    @Override
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    @Override
    public boolean validateToken(
            String token,
            UserDetails userDetails
    ) {
        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }

    @Override
    public String getUsernameFromToken(
            String token
    ) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Double getBalanceFromToken(
            String token
    ) {
        return extractClaim(token, claims -> claims.get("balance", Double.class));
    }

    @Override
    public Date getExpirationDateFromToken(
            String token
    ) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpired(
            String token
    ) {
        return !getExpirationDateFromToken(token).before(new Date());
    }
}
