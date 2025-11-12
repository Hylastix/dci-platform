/*
 * File: TokenModel.ts
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

export class TokenModel {
  constructor(
    public access_token: string,
    public expires_in: number,
    public token_type: string,
  ) {}
}
