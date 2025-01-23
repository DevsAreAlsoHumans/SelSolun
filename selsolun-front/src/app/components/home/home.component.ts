import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CryptocurrencyService } from '../../services/authentication/crypto.service';
import { MarketChartData } from '../../models/market-chart.data';
import { Chart } from 'chart.js/auto';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  prices: {
    name: string;
    timestamp: number;
    price: number;
    logo: string;
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
  chart: Chart | undefined;
  historicalPrices: { [key: string]: { timestamp: number; price: number }[] } = {};
  searchCrypto: string = ''; // For the input field

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
      this.fetchCryptoData(cryptoData.name, cryptoData.currency, cryptoData.day);
    });
  }

  fetchCryptoData(name: string, currency: string, day: number) {
    const requestData: MarketChartData = {
      name,
      currency,
      day
    };

    this.cryptoService.getMarketChart(requestData).subscribe({
      next: (response: any) => {
        if (response.body.prices) {
          const priceHistory = response.body.prices.map((entry: number[]) => ({
            timestamp: entry[0],
            price: entry[1]
          }));

          // Store historical data
          this.historicalPrices[name] = priceHistory;

          this.prices.push({
            name,
            timestamp: priceHistory[0].timestamp,
            price: priceHistory[0].price,
            logo: this.cryptoLogos[name] || 'default-logo-url'
          });

          // Optionally display a default chart
          if (!this.chart && name === 'Bitcoin') {
            this.updateChart(name, priceHistory);
          }
        } else {
          console.warn(`No prices available for ${name}`);
        }
      },
      error: (err) => {
        console.error(`Error fetching market chart data for ${name}:`, err);
      }
    });
  }

  addCrypto(cryptoName: string) {
    if (!cryptoName) {
      alert('Please enter a cryptocurrency name.');
      return;
    }

    const name = cryptoName.trim();
    if (this.prices.find(price => price.name.toLowerCase() === name.toLowerCase())) {
      alert(`${name} is already in the table.`);
      return;
    }

    this.fetchCryptoData(name, 'usd', 1);
    this.searchCrypto = ''; // Clear the input field
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

  showChart(cryptoName: string) {
    const priceHistory = this.historicalPrices[cryptoName];
    if (priceHistory) {
      this.updateChart(cryptoName, priceHistory);
    } else {
      console.warn(`No historical data available for ${cryptoName}`);
    }
  }

  updateChart(cryptoName: string, priceHistory: { timestamp: number; price: number }[]) {
    const timestamps = priceHistory.map(entry => new Date(entry.timestamp).toLocaleString());
    const prices = priceHistory.map(entry => entry.price);

    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart('cryptoChart', {
      type: 'line',
      data: {
        labels: timestamps,
        datasets: [
          {
            label: `${cryptoName} Price`,
            data: prices,
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            fill: true,
            tension: 0.4
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: true }
        },
        scales: {
          x: { title: { display: true, text: 'Time' } },
          y: { title: { display: true, text: 'Price (USD)' } }
        }
      }
    });
  }
}
