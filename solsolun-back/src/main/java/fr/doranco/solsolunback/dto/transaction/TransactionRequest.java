package fr.doranco.solsolunback.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
        @NotNull @NotBlank @JsonProperty("crypto_id") Long cryptoId,
        @NotNull @NotBlank @JsonProperty("amount") double amount
) {
}
