/*
 * File: RestApplication.java
 * Project: config
 * Created Date: 29 Jul 2025
 * Author: Clemens Albrecht
 * -----
 * Last Modified: 18 Sep 2025
 * Modified By: Clemens Albrecht
 * -----
 * Copyright (c) 2025 Hylastix GmbH
 * ------------------------------------------------------------------
 */

package com.hylastix.dci.platform.config;

import com.hylastix.dci.platform.resources.MeasurementResource;
import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;

import java.util.Set;

@ApplicationScoped
@ApplicationPath("/api")
public class RestApplication extends Application {
}
