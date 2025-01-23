package fr.doranco.solsolunback.repositories;

import fr.doranco.solsolunback.entities.Cryptocurrency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {
    Optional<Cryptocurrency> findBySymbol(String symbol);
}
