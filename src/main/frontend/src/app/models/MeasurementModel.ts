/*
 * File: MeasurementModel.ts
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

interface IMeasurementMode {
  id?: number;
  bugDensity: number;
  busFactor: number;
  codeCoverage: number;
  commentDensity: number;
  hasLicense: number;
  managesDeps: number;
  popularity: number;
  releaseFrequency: number;
  secDensity: number;
  smellDensity: number;
  usesCI: number;
  vulnDensity: number;
}

export class MeasurementModel {
  public id?: number;
  public bugDensity: number;
  public busFactor: number;
  public codeCoverage: number;
  public commentDensity: number;
  public hasLicense: number;
  public managesDeps: number;
  public popularity: number;
  public releaseFrequency: number;
  public secDensity: number;
  public smellDensity: number;
  public usesCI: number;
  public vulnDensity: number;

  constructor(obj?: IMeasurementMode) {
    this.id = obj?.id ?? -1;
    this.bugDensity = obj?.bugDensity ?? 0;
    this.busFactor = obj?.busFactor ?? 0;
    this.codeCoverage = obj?.codeCoverage ?? 0;
    this.commentDensity = obj?.commentDensity ?? 0;
    this.hasLicense = obj?.hasLicense ?? 0;
    this.managesDeps = obj?.managesDeps ?? 0;
    this.popularity = obj?.popularity ?? 0;
    this.releaseFrequency = obj?.releaseFrequency ?? 0;
    this.secDensity = obj?.secDensity ?? 0;
    this.smellDensity = obj?.smellDensity ?? 0;
    this.usesCI = obj?.usesCI ?? 0;
    this.vulnDensity = obj?.vulnDensity ?? 0;
  }
}
