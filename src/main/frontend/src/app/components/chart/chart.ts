/*
 * File: chart.ts
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

import { Component, OnInit, Signal, input, computed } from '@angular/core';
import { ChartData, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { ScoreModel } from '../../models/ScoreModel';
import { MeasurementModel } from '../../models/MeasurementModel';

@Component({
  selector: 'dci-chart',
  templateUrl: './chart.html',
  imports: [BaseChartDirective],
})
export class Chart implements OnInit {
  measurement = input.required<Signal<MeasurementModel>>();
  score = input.required<Signal<ScoreModel>>();
  trustFactors = input.required<Signal<Map<string, number>>>();
  weights = input.required<number[]>();

  labels = computed(() => {
    return Array.from(this.trustFactors()().keys());
  });

  datapoints = computed(() => {
    return Array.from(this.trustFactors()().values());
  });

  angles = computed(() => {
    return this.weights().map((weight) => weight * 360);
  });

  ngOnInit(): void {
    this.chartAreaData = {
      labels: this.labels(),
      datasets: [
        {
          data: this.datapoints(),
          label: '',
          angle: this.angles(),
        },
      ],
    };
  }

  public chartAreaData: ChartData<'polarArea'> = { datasets: [] };

  public areaLegend = true;

  public chartType: ChartType = 'polarArea';

  public chartOptions: ChartOptions = {
    responsive: true,
    scales: {
      r: {
        pointLabels: {
          display: false,
          centerPointLabels: true,
          font: {
            size: 16,
          },
        },
      },
    },
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: false,
        text: 'DCI',
      },
    },
  };
}
