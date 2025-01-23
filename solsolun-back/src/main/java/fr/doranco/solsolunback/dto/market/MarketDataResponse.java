package fr.doranco.solsolunback.dto.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketDataResponse {
    @JsonProperty("prices")
    private List<PricePoint> prices; // Liste des points de prix

    public static MarketDataResponse fromRawData(List<List<Double>> rawPrices) {
        return MarketDataResponse.builder()
                .prices(rawPrices != null
                        ? rawPrices.stream()
                            .map(PricePoint::fromRawPoint)
                            .toList()
                        : List.of())
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PricePoint {

        @JsonProperty("timestamp")
        private long timestamp;

        @JsonProperty("price")
        private double price;

        public static PricePoint fromRawPoint(List<Double> rawPoint) {
            return rawPoint != null && rawPoint.size() == 2
                    ? PricePoint.builder()
                        .timestamp(rawPoint.get(0).longValue())
                        .price(rawPoint.get(1))
                        .build()
                    : null;
        }
    }
}
