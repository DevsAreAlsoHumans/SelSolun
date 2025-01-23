import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CryptocurrencyService } from '../../services/authentication/crypto.service';
import {MarketChartData} from '../../models/market-chart.data';
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  prices: { name: string; timestamp: number; price: number }[] = [];

  constructor(private cryptoService: CryptocurrencyService) {}

  ngOnInit(): void {
    const cryptocurrencies = [
      { name: 'Bitcoin', currency: 'usd', day: 1 },
      { name: 'Ethereum', currency: 'usd', day: 1 },
      { name: 'Tether', currency: 'usd', day: 1 },
      { name: 'Doge', currency: 'usd', day: 1 },
    ];

    cryptocurrencies.forEach(cryptoData => {
      const requestData: MarketChartData = {
        name: cryptoData.name,
        currency: cryptoData.currency,
        day: cryptoData.day
      };

      this.cryptoService.getMarketChart(requestData).subscribe({
        next: (response: any) => {
          if (response.body.prices) {
            const firstPrice = response.body.prices[0];
            this.prices.push({
              name: cryptoData.name,
              timestamp: firstPrice[0],
              price: firstPrice[1]
            });
          } else {
            console.warn(`No prices available for ${cryptoData.name}`);
          }
        },
        error: (err) => {
          console.error(`Error fetching market chart data for ${cryptoData.name}:`, err);
        }
      });
    });
  }
}
