import { Component } from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import { NgForOf, NgIf } from '@angular/common';

import { LoginData } from '../../models/login-data.interface';
import { AuthService } from '../../services/authentication/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginData: LoginData = {
    email: '',
    password_hash: ''
  };

  errorMessages: string[] = [];

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(form: NgForm) {
    this.errorMessages = [];

    if (form.valid) {
      this.authService.login(this.loginData).subscribe({
        next: (response) => {
          const token = response.headers.get('Authorization') || response.headers.get('authorization');
          if (token) {
            localStorage.setItem('authToken', token);
            this.router.navigate(['/home']);
          } else {
            console.error('Aucun token trouvé dans les headers.');
          }
        },
        error: (err) => {
          console.error('Erreur lors de la connexion :', err);
          this.errorMessages.push('Identifiants invalides ou problème serveur.');
        }
      })
    } else {
      this.errorMessages.push('Veuillez remplir tous les champs correctement.');
    }
  }
}
