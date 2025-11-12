/*
 * File: JobEndpoint.java
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
import jakarta.json.JsonObjectBuilder;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/jobs")
public class JobEndpoint {
  static ConcurrentHashMap<String, Session> authenticatedSessions = new ConcurrentHashMap<>();

  public static void send(Long measurementId, String projectName, String projectVersion, String githubUrl, String purl,
      String principalName) throws IOException {
    JsonObjectBuilder builder = Json.createObjectBuilder();
    builder.add("measurement_id", measurementId);
    builder.add("project_name", projectName);
    builder.add("project_version", projectVersion);
    builder.add("github_url", githubUrl);
    builder.add("purl", purl);

    Session session = authenticatedSessions.get(principalName);

    session.getBasicRemote().sendText(builder.build().toString());
  }

  public static Set<String> getRunnerNames() {
    return authenticatedSessions.keySet();
  }

  @OnOpen
  public void openConnection(Session session) throws IOException {
    Principal user = session.getUserPrincipal();
    if (user == null) {
      session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Unauthorized"));
    } else {
      authenticatedSessions.put(session.getUserPrincipal().getName(), session);
    }
  }

  @OnMessage
  public void pongMessage(Session session, PongMessage msg) {
    System.out.println("Pong message: " +
        msg.getApplicationData().toString());
  }

  @OnClose
  public void closedConnection(Session session) {
    authenticatedSessions.remove(session.getUserPrincipal().getName());
    System.out.println("Closing session");
  }

  @OnError
  public void error(Session session, Throwable t) {
    authenticatedSessions.remove(session.getUserPrincipal().getName());
    System.out.println(t.getMessage());
  }
}
