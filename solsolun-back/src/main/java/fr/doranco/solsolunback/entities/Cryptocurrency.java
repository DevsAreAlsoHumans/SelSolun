package fr.doranco.solsolunback.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cryptocurrencies")
@EntityListeners(AuditingEntityListener.class)
public class Cryptocurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "symbol", nullable = false, unique = true, length = 10)
    private String symbol;

    @Column(name = "current_price", nullable = false)
    private double currentPrice;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "cryptocurrency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Portfolio> portfolios;

    @OneToMany(mappedBy = "cryptocurrency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
}
