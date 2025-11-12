/*
 * File: home.ts
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

import { Component, signal, inject, OnInit } from '@angular/core';
import { ScoreModel } from '../../models/ScoreModel';
import { ScoreService } from '../../services/ScoreService';

import { Event, Router, RouterLink, NavigationEnd } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.html',
})
export class HomePage {
  private readonly scoreService = inject(ScoreService);
  private readonly router = inject(Router);

  scores = signal<ScoreModel[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  private loadScores() {
    this.scoreService.getScores().subscribe({
      next: (scores) => {
        this.scores.set(scores);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err.message || 'Failed to load scores');
        this.loading.set(false);
      },
    });
  }

  constructor() {
    this.loadScores();

    this.router.events.pipe(takeUntilDestroyed()).subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.loadScores();
      }
    });
  }
}
