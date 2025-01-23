package fr.doranco.solsolunback.services.interfaces;

import fr.doranco.solsolunback.dto.crypto.CryptoRequest;
import fr.doranco.solsolunback.dto.crypto.CryptoResponse;

import fr.doranco.solsolunback.dto.market.MarketDataRequest;
import fr.doranco.solsolunback.dto.market.MarketDataResponse;

public interface ICryptocurrencyService {
    CryptoResponse saveOrUpdateCryptocurrency(CryptoRequest request);

    MarketDataResponse getMarketChart(MarketDataRequest request);
}
