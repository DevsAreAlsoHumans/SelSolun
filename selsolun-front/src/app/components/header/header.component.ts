import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { JwtService } from '../../services/jwt.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  standalone: true,
  imports: [
    CommonModule
  ],
})
export class HeaderComponent implements OnInit {

  isAuthenticated : boolean = false;
  balance: number = 0.0;
  profileMenuVisible = false;

  constructor(
    protected authService: AuthService,
    private jwtService: JwtService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.isAuthenticated.subscribe((isAuthenticated) => {
      this.isAuthenticated = isAuthenticated;
    });

    this.jwtService.getBalance().subscribe(balance => {
      if (balance !== null)
        this.balance = parseFloat(balance.toFixed(4));
    });
  }

  toggleProfileMenu() {
    this.profileMenuVisible = !this.profileMenuVisible;
  }

  navigateTo(page: string) {
    this.router.navigate([`/${page}`]).then(navigated => {
      if (!navigated) {
        console.error('La navigation vers /home a échoué.');
      }
    });
  }

  logout() {
    this.authService.logout();
  }
}
