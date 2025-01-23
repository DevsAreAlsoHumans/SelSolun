import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CryptocurrencyService } from '../../services/authentication/crypto.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  prices: { timestamp: number; price: number }[] = [];

  constructor(private cryptoService: CryptocurrencyService) {}

  ngOnInit(): void {
    const requestData = {
      name: 'Bitcoin',
      currency: 'usd',
      day: 1,
    };

    this.cryptoService.getMarketChart(requestData).subscribe({
      next: (response: any) => {
        if (response?.prices) {
          this.prices = response.prices.map((pricePoint: [number, number]) => ({
            timestamp: pricePoint[0],
            price: pricePoint[1],
          }));
        }
      },
      error: (err) => {
        console.error('Error fetching market chart data:', err);
      },
    });
  }
}
