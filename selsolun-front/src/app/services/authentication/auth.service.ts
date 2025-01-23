import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {catchError, Observable, tap, throwError} from 'rxjs';

import { environment } from '../../../environments/environment';
import { RegisterData } from '../../models/register-data.interface';
import {LoginData} from '../../models/login-data.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private api_url: string = environment.api_url + 'api/auth';

  constructor(
    private http: HttpClient
  ) { }

  register(registerData: RegisterData): Observable<any> {
    return this.http.post(
      `${this.api_url}/register`,
      registerData,
      {
        responseType: 'text',
        observe: 'response'
      }
    ).pipe(
      catchError((error) => {
        const errorMessage = error.error?.error_message || 'Une erreur inattendue s’est produite.';
        return throwError(() => new Error(errorMessage));
      })
    );
  }

  login(loginData: LoginData) {
    return this.http.post(
      `${this.api_url}/login`,
      loginData,
      {
        responseType: 'text',
        observe: 'response'
      }
    ).pipe(
      catchError((error) => {
        console.error('Erreur lors de la connexion :', error);
        const errorMessage = error.error?.error_message || 'Une erreur inattendue s’est produite.';
        return throwError(() => new Error(errorMessage));
      })
    );
  }
}
