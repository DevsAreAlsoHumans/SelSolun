import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CryptocurrencyService {
  private apiUrl = 'http://localhost:8080/api/cryptocurrencies/market-chart';
  private api_url: string = environment.api_url + 'api/cryptocurrencies/market-chart';


  constructor(private http: HttpClient) {}

  getMarketChart(data: { name: string; currency: string; day: number }): Observable<any> {
    const token = localStorage.getItem('authToken');
    console.log(token);
    // Retrieve token from localStorage

    // If token exists, set it in the Authorization header
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
    console.log(headers);
    console.log(data);

    return this.http.post(this.api_url, data, { headers }).pipe(
      catchError((error) => {
        console.error('Erreur lors de la recuperation crypto :', error);
        const errorMessage = error.error?.error_message || 'Une erreur inattendue s’est produite.';
        return throwError(() => new Error(errorMessage));
      })
    );
  }
}
