package fr.doranco.solsolunback.dto.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MarketDataRequest (
        @NotNull @NotBlank @JsonProperty("name") String name,
        @NotNull @NotBlank @JsonProperty("currency") String currency,
        @NotNull @NotBlank @JsonProperty("day") int day
) {}
