/*
 * File: createScore.ts
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
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ScoreRequestModel } from '../../models/ScoreRequestModel';
import { ScoreService } from '../../services/ScoreService';

@Component({
  selector: 'app-create-score',
  imports: [FormsModule],
  templateUrl: './createScore.html',
})
export class CreateScorePage {
  private scoreService = inject(ScoreService);
  private router: Router = inject(Router);

  submitted = false;
  scoreRequestData = new ScoreRequestModel('', '', '', '');

  onSubmit() {
    this.scoreService.createScore(this.scoreRequestData);
    this.router.navigate(['/home']);
  }
}
