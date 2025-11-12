/*
 * File: app.config.ts
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

import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {
  provideHttpClient,
  withFetch,
  withInterceptors,
  withNoXsrfProtection,
} from '@angular/common/http';
import { provideCharts, withDefaultRegisterables } from 'ng2-charts';

import { authInterceptor } from './interceptors/AuthInterceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(
      withNoXsrfProtection(),
      withFetch(),
      withInterceptors([authInterceptor]),
    ),
    provideCharts(withDefaultRegisterables()),
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
  ],
};
