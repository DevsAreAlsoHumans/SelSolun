import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CryptocurrencyService } from '../../services/authentication/crypto.service';
import {MarketChartData} from '../../models/market-chart.data';
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  prices: {
    name: string;
    timestamp: number;
    price: number;
    logo: string
  }[] = [];

  cryptoLogos: { [key: string]: string } = {
    'Bitcoin': 'https://assets.coingecko.com/coins/images/1/large/bitcoin.png',
    'Ethereum': 'https://assets.coingecko.com/coins/images/279/large/ethereum.png',
    'Tether': 'https://assets.coingecko.com/coins/images/325/large/tether.png',
    'Doge': 'https://assets.coingecko.com/coins/images/5/large/dogecoin.png',
    'Shiba': 'https://assets.coingecko.com/coins/images/11939/large/shiba.png'
  };

  sortColumn: string = 'price';
  sortDirection: 'asc' | 'desc' = 'desc';

  constructor(private cryptoService: CryptocurrencyService) {}

  ngOnInit(): void {
    const cryptocurrencies = [
      { name: 'Bitcoin', currency: 'usd', day: 1 },
      { name: 'Ethereum', currency: 'usd', day: 1 },
      { name: 'Tether', currency: 'usd', day: 1 },
      { name: 'Doge', currency: 'usd', day: 1 },
      { name: 'Shiba', currency: 'usd', day: 1 },
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
              price: firstPrice[1],

              logo: this.cryptoLogos[cryptoData.name] || 'default-logo-url'
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

  sortTable(column: string) {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'desc';
    }

    this.prices.sort((a, b) => {
      const modifier = this.sortDirection === 'asc' ? 1 : -1;
      return modifier * (a.price - b.price);
    });
  }
}
