package fr.doranco.solsolunback.services;

import fr.doranco.solsolunback.components.HttpHeadersUtil;
import fr.doranco.solsolunback.dto.authentication.LoginRequest;
import fr.doranco.solsolunback.dto.authentication.RegisterRequest;
import fr.doranco.solsolunback.entities.User;
import fr.doranco.solsolunback.exceptions.invalid.InvalidCredentialsException;
import fr.doranco.solsolunback.repositories.UserRepository;
import fr.doranco.solsolunback.services.interfaces.IAuthenticationService;
import fr.doranco.solsolunback.services.interfaces.IJwtService;
import fr.doranco.solsolunback.exceptions.alreadyExists.UserAlreadyExistsException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final HttpHeadersUtil httpHeadersUtil;

    private final IJwtService jwtService;

    @Override
    public ResponseEntity<String> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email()))
            throw new UserAlreadyExistsException("error.user.already-exists");

        final User user = User.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.passwordHash()))
                .balance(1000000000.0)
                .build();

        this.userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("success.user.created");
    }

    @Override
    public ResponseEntity<String> login(LoginRequest request) {
        final User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("error.authentication.invalid-credentials"));

        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.passwordHash()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            final String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok()
                    .headers(httpHeadersUtil.createHeaders(token))
                    .body("success.authentication");
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("error.authentication.invalid-credentials");
        }
    }
}
