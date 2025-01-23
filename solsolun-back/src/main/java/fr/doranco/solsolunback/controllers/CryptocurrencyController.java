package fr.doranco.solsolunback.controllers;

import fr.doranco.solsolunback.dto.crypto.CryptoRequest;
import fr.doranco.solsolunback.dto.crypto.CryptoResponse;
import fr.doranco.solsolunback.dto.market.MarketDataRequest;
import fr.doranco.solsolunback.dto.market.MarketDataResponse;
import fr.doranco.solsolunback.services.interfaces.ICryptocurrencyService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cryptocurrencies")
@RequiredArgsConstructor
public class CryptocurrencyController {
    private final ICryptocurrencyService cryptocurrencyService;

    @PostMapping("/update-price")
    public ResponseEntity<CryptoResponse> updateCryptocurrencyPrice(
            @RequestBody CryptoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.cryptocurrencyService.saveOrUpdateCryptocurrency(request));
    }

    @GetMapping("/market-chart")
    public ResponseEntity<MarketDataResponse> getMarketChart(
            @RequestBody MarketDataRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.cryptocurrencyService.getMarketChart(request));
    }
}
