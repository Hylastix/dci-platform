/*
 * File: LoginHandler.java
 * Project: platform
 * Created Date: 29 Jul 2025
 * Author: Clemens Albrecht
 * -----
 * Last Modified: 18 Sep 2025
 * Modified By: Clemens Albrecht
 * -----
 * Copyright (c) 2025 Hylastix GmbH
 * ------------------------------------------------------------------
 */

package com.hylastix.dci.platform;

import jakarta.json.Json;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ibm.websphere.security.jwt.JwtBuilder;
import com.ibm.websphere.security.jwt.Claims;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/login")
public class LoginHandler extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    if (username == null || username.trim().isEmpty()) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username required");
      return;
    }

    if (password == null || password.trim().isEmpty()) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password required");
      return;
    }

    try {
      req.login(username, password);
    } catch (ServletException e) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Username or password wrong");
    }

    String remoteUser = req.getRemoteUser();
    Set<String> roles = getRoles(req);
    if (remoteUser != null && remoteUser.equals(username)) {
      try {
        var jwt = buildJwt(username, roles);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        var body = Json.createObjectBuilder().add("access_token", jwt).add("expires_in", 86400)
            .add("token_type", "Bearer").build();
        resp.getWriter().write(body.toString());
      } catch (Exception e) {
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Building JWT failed");
      }
    } else {
      System.out.println("Failed return JWT.");
    }
  }

  private String buildJwt(String userName, Set<String> roles) throws Exception {

    return JwtBuilder.create("dciJwtBuilder").claim(Claims.SUBJECT, userName).claim("upn", userName)
        .claim("groups", roles.toArray(new String[roles.size()])).buildJwt().compact();
  }

  private Set<String> getRoles(HttpServletRequest request) {
    Set<String> roles = new HashSet<>();
    boolean isAdmin = request.isUserInRole("admin");
    boolean isUser = request.isUserInRole("users");
    boolean isService = request.isUserInRole("services");
    if (isAdmin) {
      roles.add("admin");
    }
    if (isUser) {
      roles.add("users");
    }
    if (isService) {
      roles.add("services");
    }
    return roles;
  }
}
