/*
 * File: scoreDetail.ts
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

import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { ScoreService } from '../../services/ScoreService';
import { ActivatedRoute } from '@angular/router';
import { ScoreModel } from '../../models/ScoreModel';
import { MeasurementService } from '../../services/MeasurementService';
import { MeasurementModel } from '../../models/MeasurementModel';
import { RouterLink } from '@angular/router';
import { Chart } from '../../components/chart/chart';

@Component({
  selector: 'app-home',
  imports: [RouterLink, Chart],
  templateUrl: './scoreDetail.html',
})
export class ScoreDetailPage implements OnInit {
  scoreService = inject(ScoreService);
  private readonly measurementService = inject(MeasurementService);
  private activatedRoute = inject(ActivatedRoute);

  weights = [0.277, 0.164, 0.092, 0.072, 0.073, 0.14, 0.049, 0.096, 0.037];

  score = signal<ScoreModel>(new ScoreModel());
  measurement = signal<MeasurementModel>(new MeasurementModel());
  scoreGrade = computed(() => {
    let value = this.score().value;
    if (value > 0.8) {
      return 'A';
    } else if (value > 0.6) {
      return 'B';
    } else if (value > 0.4) {
      return 'C';
    } else if (value > 0.2) {
      return 'D';
    } else {
      return 'E';
    }
  });

  trustFactors = computed(() => {
    let trustMap = new Map<string, number>();
    trustMap.set(
      'Security',
      (this.measurement().secDensity + this.measurement().vulnDensity) / 2.0,
    );
    trustMap.set(
      'Source Code Quality',
      (this.measurement().smellDensity + this.measurement().bugDensity) / 2.0,
    );
    trustMap.set(
      'Documentation Completeness',
      this.measurement().commentDensity,
    );
    trustMap.set('License Declaration', this.measurement().hasLicense);
    trustMap.set(
      'Development Process Quality',
      (this.measurement().usesCI + this.measurement().codeCoverage) / 2.0,
    );
    trustMap.set('Project Health', this.measurement().busFactor);
    trustMap.set('Release Cadence', this.measurement().releaseFrequency);
    trustMap.set('Dependency Management', this.measurement().managesDeps);
    trustMap.set('Reputation', this.measurement().popularity);

    return trustMap;
  });

  ngOnInit() {
    this.loadData();
  }

  private loadData() {
    this.activatedRoute.params.subscribe((params) => {
      this.scoreService.getScore(parseInt(params['id'])).subscribe((score) => {
        this.score.set(score);
        this.measurementService
          .getMeasurement(score.id)
          .subscribe((measurement) => {
            this.measurement.set(measurement);
          });
      });
    });
  }
}
