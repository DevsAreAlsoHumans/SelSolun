package fr.doranco.selsolunback.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
public record RegisterRequest(
        @NotBlank @NotNull String username,
        @Email String email,
        @NotBlank String password
) {

}
