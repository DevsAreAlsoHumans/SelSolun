package fr.doranco.solsolunback.repositories;

import fr.doranco.solsolunback.entities.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
