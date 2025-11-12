/*
 * File: ScoreService.ts
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

import { ScoreModel } from '../models/ScoreModel';
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ScoreRequestModel } from '../models/ScoreRequestModel';

@Injectable({
  providedIn: 'root',
})
export class ScoreService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl: string = 'http://localhost:9080/api/score';

  getScores(): Observable<ScoreModel[]> {
    return this.http.get<ScoreModel[]>(this.apiUrl);
  }

  getScore(id: number): Observable<ScoreModel> {
    return this.http.get<ScoreModel>(`${this.apiUrl}/${id}`);
  }

  createScore(scoreReq: ScoreRequestModel) {
    this.http.post<ScoreModel>(this.apiUrl, scoreReq).subscribe({
      next: (response) => {},
      error: (error) => {},
    });
  }

  deleteScore(id: number) {
    this.http.delete(`${this.apiUrl}/${id}`).subscribe({
      next: (response) => {},
      error: (error) => {},
    });
  }
}
