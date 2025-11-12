/*
 * File: app.routes.ts
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

import { Routes } from "@angular/router";
import { LoginPage } from "./pages/login/login";
import { HomePage } from "./pages/home/home";
import { GreeterPage } from "./pages/greeter/greeter";
import { authGuard } from "./auth-guard";
import { CreateScorePage } from "./pages/createScore/createScore";
import { ScoreDetailPage } from "./pages/scoreDetail/scoreDetail";

export const routes: Routes = [
  {
    path: "",
    component: GreeterPage,
    title: "Greetings",
  },
  {
    path: "login",
    component: LoginPage,
    title: "Login",
  },
  {
    path: "home",
    component: HomePage,
    title: "Home",
    canActivate: [authGuard],
  },
  {
    path: "create-score",
    component: CreateScorePage,
    title: "Create Score",
    canActivate: [authGuard],
  },
  {
    path: "score/:id",
    component: ScoreDetailPage,
    title: "Score Detail",
    canActivate: [authGuard],
  },
];
