import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';

import { RegisterData } from '../../models/register-data.interface';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerData: RegisterData = {
    username: '',
    email: '',
    password_hash: ''
  };

  errorMessages: string[] = [];

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  validateEmail() {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (this.registerData.email !== '' && !emailRegex.test(this.registerData.email)) {
      this.errorMessages.push('Veuillez entrer une adresse email valide.');
    }
  }

  validatePassword() {
    const criteria = [
      { regex: /.{12,}/, message: '12 caractères minimum' },
      { regex: /[a-z]/, message: 'une lettre minuscule' },
      { regex: /[A-Z]/, message: 'une lettre majuscule' },
      { regex: /\d/, message: 'un chiffre' },
      { regex: /[^a-zA-Z0-9]/, message: 'un caractère spécial (ex. !@#$%^&*())' },
    ];

    const passwordErrors = criteria
      .filter((criterion) => !criterion.regex.test(this.registerData.password_hash))
      .map((criterion) => criterion.message);

    if (passwordErrors.length > 0) {
      this.errorMessages.push(
        `Le mot de passe doit contenir : ${passwordErrors.join(', ')}.`
      );
    }
  }

  onSubmit(form: NgForm) {
    this.errorMessages = [];

    if (form.valid && this.errorMessages.length === 0) {
      this.authService.register(this.registerData).subscribe({
        next: () => {
          this.router.navigate(['/login']);
        },
        error: (err) => {
          console.error('Erreur lors de l’inscription :', err);
        }
      });
    } else {
      this.errorMessages.push('Veuillez remplir tous les champs.');
    }
  }
}
