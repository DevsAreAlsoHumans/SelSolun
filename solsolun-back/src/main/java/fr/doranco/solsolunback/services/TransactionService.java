package fr.doranco.solsolunback.services;

import fr.doranco.solsolunback.dto.transaction.TransactionRequest;
import fr.doranco.solsolunback.dto.transaction.TransactionResponse;
import fr.doranco.solsolunback.entities.Cryptocurrency;
import fr.doranco.solsolunback.entities.Portfolio;
import fr.doranco.solsolunback.entities.Transaction;
import fr.doranco.solsolunback.entities.User;
import fr.doranco.solsolunback.enums.TransactionType;
import fr.doranco.solsolunback.repositories.CryptocurrencyRepository;
import fr.doranco.solsolunback.repositories.PortfolioRepository;
import fr.doranco.solsolunback.repositories.TransactionRepository;
import fr.doranco.solsolunback.repositories.UserRepository;
import fr.doranco.solsolunback.services.interfaces.ITransactionService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final UserRepository userRepository;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final PortfolioRepository portfolioRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransactionResponse buyCrypto(TransactionRequest request) {
        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'email : " + email));

        final Cryptocurrency cryptocurrency = this.cryptocurrencyRepository.findById(request.cryptoId())
                .orElseThrow(() -> new RuntimeException("Crypto introuvable avec ID : " + request.cryptoId()));

        final double totalCost = request.amount() * cryptocurrency.getCurrentPrice();

        // TODO Ajouter un controle de la balance du user et ajouter le champs balance dans l'entity

        final Portfolio portfolio = portfolioRepository.findByUserAndCryptocurrency(user, cryptocurrency)
                .map(existingPortfolio -> {
                    existingPortfolio.setQuantity(existingPortfolio.getQuantity() + request.amount());
                    existingPortfolio.setLastUpdated(LocalDateTime.now());
                    return portfolioRepository.save(existingPortfolio);
                })
                .orElseGet(() -> {
                    final Portfolio newPortfolio = Portfolio.builder()
                            .user(user)
                            .cryptocurrency(cryptocurrency)
                            .quantity(request.amount())
                            .lastUpdated(LocalDateTime.now())
                            .build();
                    return portfolioRepository.save(newPortfolio);
                });

        final Transaction transaction = Transaction.builder()
                .user(user)
                .cryptocurrency(cryptocurrency)
                .quantity(request.amount())
                .priceAtTransaction(cryptocurrency.getCurrentPrice())
                .transactionType(TransactionType.BUY)
                .timestamp(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .transactionId(transaction.getId())
                .userId(user.getId())
                .cryptoName(cryptocurrency.getName())
                .amount(transaction.getQuantity())
                .totalCost(totalCost)
                .portfolioQuantity(portfolio.getQuantity())
                .createdAt(transaction.getTimestamp())
                .build();
    }
}
