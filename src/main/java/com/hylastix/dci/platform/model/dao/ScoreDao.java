/*
 * File: ScoreDao.java
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
public class ScoreDao {
  @PersistenceContext(name = "score")
  private EntityManager em;

  @Transactional
  public void createScore(Score score) {
    em.persist(score);
  }

  @Transactional
  public Score readScore(Long id) {
    return em.find(Score.class, id);
  }

  @Transactional
  public void updateScoreValue(Score score, Double value) {
    score.setValue(value);
    this.updateScore(score);
  }

  @Transactional
  public void updateScore(Score score) {
    em.merge(score);
  }

  @Transactional
  public void deleteScore(Long id) {
    var score = em.find(Score.class, id);
    em.remove(score);
  }

  @Transactional
  public List<Score> readAllScores() {
    return em.createNamedQuery("Score.findAll", Score.class).getResultList();
  }

  @Transactional
  public Score getByMeasurement(Measurement measurement) {
    return em.createNamedQuery("Score.findByMeasurement", Score.class).setParameter("measurement", measurement)
        .getResultList().getFirst();
  }
}
