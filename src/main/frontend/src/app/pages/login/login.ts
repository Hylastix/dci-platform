/*
 * File: login.ts
 * Project: frontend
 * Created Date: 01 Aug 2025
 * Author: Clemens Albrecht
 * -----
 * Last Modified: 18 Sep 2025
 * Modified By: Clemens Albrecht
 * -----
 * Copyright (c) 2025 Hylastix GmbH
 * ------------------------------------------------------------------
 */

import { Component, inject } from '@angular/core';
import { LoginModel } from '../../models/LoginModel';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/LoginService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
})
export class LoginPage {
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  submitted = false;
  loginData = new LoginModel('', '');

  onSubmit() {
    this.authService.login(this.loginData);
    this.router.navigate(['/home']);
  }
}
