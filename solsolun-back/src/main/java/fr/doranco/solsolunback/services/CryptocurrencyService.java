package fr.doranco.solsolunback.services;

import fr.doranco.solsolunback.dto.crypto.CryptoRequest;
import fr.doranco.solsolunback.dto.crypto.CryptoResponse;
import fr.doranco.solsolunback.dto.market.MarketDataRequest;
import fr.doranco.solsolunback.dto.market.MarketDataResponse;
import fr.doranco.solsolunback.entities.Cryptocurrency;
import fr.doranco.solsolunback.repositories.CryptocurrencyRepository;
import fr.doranco.solsolunback.services.interfaces.ICryptocurrencyService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CryptocurrencyService implements ICryptocurrencyService {
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final RestTemplate restTemplate;

    private static final String API_URL = "https://api.coingecko.com/api/v3";

    @Override
    @Transactional
    public CryptoResponse saveOrUpdateCryptocurrency(CryptoRequest request) {
        final String apiUrl = String.format(
                "%s/simple/price?ids=%s&vs_currencies=%s",
                API_URL,
                request.name().toLowerCase(),
                request.currency().toLowerCase()
        );

        var response = restTemplate.getForObject(apiUrl, Map.class);

        if (response != null && response.containsKey(request.name().toLowerCase())) {
            final Map<?, ?> priceData = (Map<?, ?>) response.get(request.name().toLowerCase());

            final Object priceObject = priceData.get(request.currency().toLowerCase());
            final double price;

            if (priceObject instanceof Integer)
                price = ((Integer) priceObject).doubleValue();
            else if (priceObject instanceof Double)
                price = (Double) priceObject;
            else
                throw new RuntimeException("Unexpected price type: " + priceObject.getClass());

            final Cryptocurrency updatedCrypto = cryptocurrencyRepository.findBySymbol(request.symbol())
                    .map(existingCrypto -> {
                        existingCrypto.setCurrentPrice(price);
                        existingCrypto.setUpdatedAt(LocalDateTime.now());

                        return cryptocurrencyRepository.save(existingCrypto);
                    })
                    .orElseGet(() -> {
                        Cryptocurrency newCrypto = Cryptocurrency.builder()
                                .name(request.name())
                                .symbol(request.symbol())
                                .currentPrice(price)
                                .updatedAt(LocalDateTime.now())
                                .build();

                        return cryptocurrencyRepository.save(newCrypto);
                    });

            return CryptoResponse.builder()
                    .id(updatedCrypto.getId())
                    .name(updatedCrypto.getName())
                    .symbol(updatedCrypto.getSymbol())
                    .currentPrice(updatedCrypto.getCurrentPrice())
                    .updatedAt(updatedCrypto.getUpdatedAt().toEpochSecond(ZoneOffset.UTC))
                    .build();
        }

        throw new RuntimeException("Could not fetch price for symbol: " + request.symbol());
    }

    @Override
    @Transactional
    public Object getMarketChart(MarketDataRequest request) {
        final String apiUrl = String.format(
                "%s/coins/%s/market_chart?vs_currency=%s&days=%d",
                API_URL,
                request.name().toLowerCase(),
                request.currency().toLowerCase(),
                request.day()
        );

        try {

            return restTemplate.getForObject(apiUrl, Object.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel à l'API CoinGecko : " + e.getMessage());
            throw new RuntimeException("Impossible de récupérer les données de marché pour la crypto " + request.name());
        }
    }
}
