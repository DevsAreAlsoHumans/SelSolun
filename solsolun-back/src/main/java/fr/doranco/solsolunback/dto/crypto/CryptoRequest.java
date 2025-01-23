package fr.doranco.solsolunback.dto.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CryptoRequest(
        @NotNull @NotBlank @JsonProperty("name") String name,
        @NotNull @NotBlank @JsonProperty("symbol") String symbol,
        @NotNull @NotBlank @JsonProperty("currency") String currency
) {
}
