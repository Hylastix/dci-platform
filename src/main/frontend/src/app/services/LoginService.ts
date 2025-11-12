/*
 * File: LoginService.ts
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

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserModel } from '../models/UserModel';
import { LoginModel } from '../models/LoginModel';
import { TokenModel } from '../models/TokenModel';
import { BehaviorSubject } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly TOKEN_KEY = 'access_token';
  private currentUserSubject = new BehaviorSubject<UserModel | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    // Check if user is already logged in on service initialization
    this.initializeAuth();
  }

  /**
   * Initialize authentication state from stored token
   */
  private initializeAuth(): void {
    const token = this.getStoredToken();
    if (token && this.isTokenValid(token)) {
      // Decode token to get user info (basic implementation)
      const user = this.decodeToken(token);
      this.currentUserSubject.next(user);
    } else {
      this.removeToken();
    }
  }

  /**
   * Login with username and password
   */
  login(credentials: LoginModel) {
    // Create URL-encoded form data
    const formData = new URLSearchParams();
    formData.append('username', credentials.username);
    formData.append('password', credentials.password);

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });

    return this.http
      .post<TokenModel>('http://localhost:9080/login', formData.toString(), {
        headers,
      })
      .subscribe((token) => {
        if (token.access_token) {
          this.setToken(token.access_token);
          const user = this.decodeToken(token.access_token);
          this.currentUserSubject.next(user);
        }
        localStorage.setItem('access_token', token.access_token);
      });
  }

  /**
   * Logout user and clear stored data
   */
  logout(): void {
    this.removeToken();
    this.currentUserSubject.next(null);
  }

  /**
   * Check if user is currently authenticated
   */
  isAuthenticated(): boolean {
    const token = this.getStoredToken();
    return token !== null && this.isTokenValid(token);
  }

  /**
   * Get current authentication token
   */
  getToken(): string | null {
    return this.getStoredToken();
  }

  /**
   * Get current user
   */
  getCurrentUser(): UserModel | null {
    return this.currentUserSubject.value;
  }

  /**
   * Store JWT token in localStorage
   */
  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  /**
   * Remove JWT token from localStorage
   */
  private removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  /**
   * Get JWT token from localStorage
   */
  private getStoredToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  /**
   * Basic JWT token validation (checks expiration)
   */
  private isTokenValid(token: string): boolean {
    try {
      const payload = jwtDecode(token);
      const currentTime = Math.floor(Date.now() / 1000);

      // Check if token has expired
      if (payload.exp && payload.exp < currentTime) {
        return false;
      }

      return true;
    } catch (error) {
      return false;
    }
  }

  /**
   * Decode JWT token to get user information
   */
  private decodeToken(token: string): UserModel {
    try {
      const payload = jwtDecode(token);
      if (payload.sub != undefined) {
        return new UserModel(payload.sub);
      } else {
        throw new Error("JWT doesn't contain sub attribute");
      }
    } catch (error) {
      throw new Error('Invalid token format');
    }
  }
}
