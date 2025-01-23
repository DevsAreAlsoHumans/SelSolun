package fr.doranco.solsolunback.repositories;

import fr.doranco.solsolunback.entities.Cryptocurrency;
import fr.doranco.solsolunback.entities.Portfolio;
import fr.doranco.solsolunback.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUserAndCryptocurrency(User user, Cryptocurrency cryptocurrency);
}
