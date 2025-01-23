package fr.doranco.solsolunback.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    @NotNull @NotBlank @JsonProperty("transaction_id")
    private Long transactionId;

    @NotNull @NotBlank @JsonProperty("user_id")
    private Long userId;

    @NotNull @NotBlank @JsonProperty("crypto_name")
    private String cryptoName;

    @NotNull @NotBlank @JsonProperty("amount")
    private double amount;

    @NotNull @NotBlank @JsonProperty("total_cost")
    private double totalCost;

    @NotNull @NotBlank @JsonProperty("portfolio_quantity")
    private double portfolioQuantity;

    @NotNull @NotBlank @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

