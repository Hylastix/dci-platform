/*
 * File: MeasurementDao.java
 * Project: dao
 * Created Date: 29 Jul 2025
 * Author: Clemens Albrecht
 * -----
 * Last Modified: 18 Sep 2025
 * Modified By: Clemens Albrecht
 * -----
 * Copyright (c) 2025 Hylastix GmbH
 * ------------------------------------------------------------------
 */

package com.hylastix.dci.platform.model.dao;

import com.hylastix.dci.platform.model.entity.Measurement;
import com.hylastix.dci.platform.model.entity.Score;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class MeasurementDao {
  @PersistenceContext(name = "measurements")
  private EntityManager em;

  @Transactional
  public void createMeasurement(Measurement measurement) {
    em.persist(measurement);
  }

  @Transactional
  public Measurement createEmptyMeasurement(Score score) {
    var measurement = new Measurement();
    measurement.setVulnDensity(0.0);
    measurement.setSecDensity(0.0);
    measurement.setBugDensity(0.0);
    measurement.setSmellDensity(0.0);
    measurement.setCommentDensity(0.0);
    measurement.setHasLicense(0.0);
    measurement.setUsesCI(0.0);
    measurement.setCodeCoverage(0.0);
    measurement.setBusFactor(0.0);
    measurement.setReleaseFrequency(0.0);
    measurement.setManagesDeps(0.0);
    measurement.setPopularity(0.0);
    em.persist(measurement);
    em.flush();

    return measurement;
  }

  @Transactional
  public Measurement readMeasurement(Long id) {
    return em.find(Measurement.class, id);
  }

  @Transactional
  public void updateMeasurement(Measurement measurement) {
    em.merge(measurement);
  }

  @Transactional
  public void deleteMeasurement(Measurement measurement) {
    em.remove(measurement);
  }

  @Transactional
  public List<Measurement> readAllMeasurements() {
    return em.createNamedQuery("Measurement.findAll", Measurement.class).getResultList();
  }
}
