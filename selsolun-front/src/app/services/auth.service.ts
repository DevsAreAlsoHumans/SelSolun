import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { BehaviorSubject, catchError, Observable, throwError } from 'rxjs';

import { environment } from '../../environments/environment';
import { RegisterData } from '../models/register-data.interface';
import { LoginData } from '../models/login-data.interface';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private api_url: string = environment.api_url + 'auth';

  private authStatus = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient,
    private jwtService: JwtService
  ) {
    this.jwtService.validate().subscribe((isValid) => this.authStatus.next(isValid));
  }

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
        return throwError(() => new Error(error.error?.error_message || 'Une erreur inattendue s’est produite.'));
      })
    );
  }

  get isAuthenticated(): Observable<boolean> {
    return this.authStatus.asObservable();
  }

  loginIn(token: string) {
    localStorage.setItem('authToken', token);
    this.authStatus.next(true);
  }

  logout() {
    localStorage.removeItem('authToken');
    this.authStatus.next(false);
  }
}
