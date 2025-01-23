package fr.doranco.solsolunback.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotNull @NotBlank @Email @JsonProperty("email") String email,
        @NotNull @NotBlank @JsonProperty("password_hash") String passwordHash
) {
}
