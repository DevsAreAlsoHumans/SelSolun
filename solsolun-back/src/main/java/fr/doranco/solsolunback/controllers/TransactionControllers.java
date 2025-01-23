package fr.doranco.solsolunback.controllers;

import fr.doranco.solsolunback.dto.transaction.TransactionRequest;
import fr.doranco.solsolunback.dto.transaction.TransactionResponse;
import fr.doranco.solsolunback.services.interfaces.ITransactionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionControllers {

    private final ITransactionService transactionService;

    @PostMapping("/buy")
    public ResponseEntity<TransactionResponse> buy (
            @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.transactionService.buyCrypto(request));
    }
}
