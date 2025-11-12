/*
 * File: MeasurementResource.java
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

import com.hylastix.dci.platform.model.dao.MeasurementDao;
import com.hylastix.dci.platform.model.dao.ScoreDao;
import com.hylastix.dci.platform.model.entity.Measurement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
@Path("/measurement")
@RolesAllowed({ "users" })
public class MeasurementResource {
  @Inject
  private MeasurementDao measurementDao;

  @Inject
  private ScoreDao scoreDao;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMeasurements() {
    List<Measurement> measurements = measurementDao.readAllMeasurements();
    return Response.ok(measurements).build();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMeasurement(@PathParam("id") @NotNull Long id) {
    var measurement = measurementDao.readMeasurement(id);
    return Response.ok(measurement).build();
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed({ "services" })
  @Transactional
  public Response updateMeasurement(@PathParam("id") @NotNull Long id, @Valid Measurement measurement) {
    measurement.setId(id);

    System.out.println(measurement);
    measurementDao.updateMeasurement(measurement);

    var updatedMeasurement = measurementDao.readMeasurement(id);
    var score = scoreDao.getByMeasurement(updatedMeasurement);
    System.out.println(score);
    scoreDao.updateScoreValue(score, measurement.getScoreValueWeighted());
    return Response.ok(measurement).build();
  }
}
