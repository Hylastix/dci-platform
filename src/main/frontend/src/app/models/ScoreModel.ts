/*
 * File: ScoreModel.ts
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

interface IScoreModel {
  id: number;
  projectName: string;
  projectVersion: string;
  githubUrl: string;
  purl: string;
  value: number;
  measurement: number;
}

export class ScoreModel {
  public id: number;
  public projectName: string;
  public projectVersion: string;
  public githubUrl: string;
  public purl: string;
  public value: number;
  public measurement: number;

  constructor(obj?: IScoreModel) {
    this.id = obj?.id ?? -1;
    this.projectName = obj?.projectName ?? '';
    this.projectVersion = obj?.projectVersion ?? '';
    this.githubUrl = obj?.githubUrl ?? '';
    this.purl = obj?.purl ?? '';
    this.value = obj?.value ?? 0;
    this.measurement = obj?.measurement ?? 0;
  }
}
