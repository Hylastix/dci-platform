/*
 * File: ScoreResource.java
 * Project: resources
 * Created Date: 29 Jul 2025
 * Author: Clemens Albrecht
 * -----
 * Last Modified: 18 Sep 2025
 * Modified By: Clemens Albrecht
 * -----
 * Copyright (c) 2025 Hylastix GmbH
 * ------------------------------------------------------------------
 */

package com.hylastix.dci.platform.resources;

import com.hylastix.dci.platform.JobEndpoint;
import com.hylastix.dci.platform.model.dao.MeasurementDao;
import com.hylastix.dci.platform.model.dao.ScoreDao;
import com.hylastix.dci.platform.model.entity.Score;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@RequestScoped
@Path("/score")
@RolesAllowed({ "users" })
public class ScoreResource {

  @Inject
  private ScoreDao scoreDao;

  @Inject
  private MeasurementDao measurementDao;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getScores() {
    List<Score> scores = scoreDao.readAllScores();
    return Response.ok(scores).build();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getScore(@PathParam("id") @NotNull Long id) {
    var score = scoreDao.readScore(id);
    return Response.ok(score).build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteScore(@PathParam("id") @NotNull Long id) {
    scoreDao.deleteScore(id);
    return Response.ok().status(200).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response createScore(@Valid Score score) {
    // Ensure that user can't create this with a score, even though it will be
    // overwritten later
    score.setValue(null);

    scoreDao.createScore(score);

    var measurement = measurementDao.createEmptyMeasurement(score);
    score.setMeasurement(measurement);
    scoreDao.updateScore(score);
    measurementDao.updateMeasurement(measurement);

    var runners = JobEndpoint.getRunnerNames();

    if (runners.isEmpty()) {
      throw new WebApplicationException(
          "No runnner available",
          Response.Status.SERVICE_UNAVAILABLE);
    }

    var currentRunner = runners.iterator().next();

    try {
      JobEndpoint.send(
          measurement.getId(),
          score.getProjectName(),
          score.getProjectVersion(),
          score.getGithubUrl(),
          score.getPurl(),
          currentRunner);
    } catch (IOException e) {
      throw new WebApplicationException(
          "Communication with runner failed",
          Response.Status.INTERNAL_SERVER_ERROR);
    }

    return Response.status(Response.Status.CREATED).entity(score).build();
  }
}
