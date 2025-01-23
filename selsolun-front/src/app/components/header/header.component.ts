import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';

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
  profileMenuVisible = false;

  constructor(
    protected authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.isAuthenticated.subscribe((isAuthenticated) => {
      this.isAuthenticated = isAuthenticated;
    });


    console.log(this.isAuthenticated);
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
