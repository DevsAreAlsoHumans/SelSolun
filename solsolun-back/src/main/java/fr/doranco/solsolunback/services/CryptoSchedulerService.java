package fr.doranco.solsolunback.services;

import fr.doranco.solsolunback.dto.crypto.CryptoRequest;
import fr.doranco.solsolunback.services.interfaces.ICryptoSchedulerService;
import fr.doranco.solsolunback.services.interfaces.ICryptocurrencyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoSchedulerService implements ICryptoSchedulerService {
    private final ICryptocurrencyService cryptocurrencyService;

    private static final List<Map<String, String>> TOP_10_CRYPTOS = List.of(
            Map.of("name", "bitcoin", "symbol", "btc"),
            Map.of("name", "ethereum", "symbol", "eth"),
            Map.of("name", "tether", "symbol", "usdt"),
            Map.of("name", "binancecoin", "symbol", "bnb"),
            Map.of("name", "usd-coin", "symbol", "usdc")
    );

    @Override
    @Transactional
    //@Scheduled(fixedRate = 600000)
    public void updateTopCryptocurrencies() {
        for (Map<String, String> crypto : TOP_10_CRYPTOS) {
            try {
                final String name = crypto.get("name");
                final String symbol = crypto.get("symbol");

                final CryptoRequest request = new CryptoRequest(name, symbol, "usd");
                cryptocurrencyService.saveOrUpdateCryptocurrency(request);
            } catch (Exception e) {
                log.error("Une erreur s'est produite : {}", e.getMessage(), e);
            }
        }
    }
}
