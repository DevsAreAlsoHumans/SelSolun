import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  standalone: true,
  imports: [
    CommonModule
  ],
})
export class HeaderComponent {

  isAuthenticated : boolean = false;
  profileMenuVisible = false;

  constructor(
    private router: Router
  ) {}

  toggleProfileMenu() {
    this.profileMenuVisible = !this.profileMenuVisible;
  }

  navigateTo(page: string) {
    this.router.navigate([`/${page}`]);
  }

  logout() {
    console.log('Déconnexion');
    this.isAuthenticated = false;
    this.router.navigate(['/login']); // Redirige vers la page de connexion
  }
}
