import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { catchError, map, Observable, of } from 'rxjs';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  private api_url: string = environment.api_url + 'jwt';

  constructor(
    private http: HttpClient
  ) { }

  validate(): Observable<boolean> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('authToken'));

    return this.http.get<boolean>(
      `${this.api_url}/validate`,
      { headers }
    ).pipe(
      map(response => response),
      catchError((err) => {
        console.error('Erreur lors de la validation du token :', err);
        return of(false);
      })
    );
  }

  getBalance(): Observable<number | null> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('authToken'));

    return this.http.get<number>(
      `${this.api_url}/balance`,
      { headers }
    ).pipe(
      map(response => response),
      catchError(() => of(null))
    );
  }
}
