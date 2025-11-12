/*
 * File: MeasurementService.ts
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

import { MeasurementModel } from "../models/MeasurementModel";
import { Injectable, inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class MeasurementService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl: string = "http://localhost:9080/api/measurement";

  getMeasurements(): Observable<MeasurementModel[]> {
    return this.http.get<MeasurementModel[]>(this.apiUrl);
  }

  getMeasurement(id: number): Observable<MeasurementModel> {
    return this.http.get<MeasurementModel>(`${this.apiUrl}/${id}`);
  }
}
