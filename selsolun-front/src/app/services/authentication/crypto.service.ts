import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {MarketChartData} from '../../models/market-chart.data';

@Injectable({
  providedIn: 'root',
})
export class CryptocurrencyService {
  private apiUrl = 'http://localhost:8080/api/cryptocurrencies/market-chart'; // Update with the actual URL

  constructor(private http: HttpClient) {}

  getMarketChart(data: MarketChartData): Observable<any> {
    const token = localStorage.getItem('authToken'); // Retrieve auth token

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
    return this.http.post(
      this.apiUrl,
      data,
      {
        headers,
        observe: 'response'
      }
    ).pipe(
      catchError((error) => {
        console.error('Error fetching market chart:', error);
        const errorMessage = error.error?.error_message || 'An unexpected error occurred.';
        return throwError(() => new Error(errorMessage));
      })
    );
  }
}
