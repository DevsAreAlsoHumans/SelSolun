package fr.doranco.solsolunback.services.interfaces;

import fr.doranco.solsolunback.dto.transaction.TransactionRequest;
import fr.doranco.solsolunback.dto.transaction.TransactionResponse;

public interface ITransactionService {
    TransactionResponse buyCrypto(TransactionRequest request);
}
