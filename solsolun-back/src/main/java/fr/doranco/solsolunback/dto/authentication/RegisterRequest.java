package fr.doranco.solsolunback.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotNull @NotBlank @JsonProperty("username") String username,
        @NotNull @NotBlank @JsonProperty("email") @Email String email,
        @NotNull @NotBlank @JsonProperty("password_hash") String passwordHash
) {
}
