/*
 * File: Score.java
 * Project: entity
 * Created Date: 29 Jul 2025
 * Author: Clemens Albrecht
 * -----
 * Last Modified: 18 Sep 2025
 * Modified By: Clemens Albrecht
 * -----
 * Copyright (c) 2025 Hylastix GmbH
 * ------------------------------------------------------------------
 */

package com.hylastix.dci.platform.model.entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Score")
@NamedQuery(name = "Score.findAll", query = "SELECT s FROM Score s")
@NamedQuery(name = "Score.findByMeasurement", query = "SELECT s FROM Score s WHERE s.measurement = :measurement")
public class Score implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "projectName")
  private String projectName;

  @Column(name = "projectVersion")
  private String projectVersion;

  @Column(name = "value")
  private Double value = 0.0;

  @Column(name = "purl")
  private String purl;

  @Column(name = "githubUrl")
  private String githubUrl;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "measurementId")
  @JsonbTransient
  private Measurement measurement;

  public Measurement getMeasurement() {
    return measurement;
  }

  @JsonbProperty("measurement")
  public Long getMeasurementAsId() {
    return measurement.getId();
  }

  public void setMeasurement(Measurement measurement) {
    this.measurement = measurement;
  }

  public String getGithubUrl() {
    return githubUrl;
  }

  public void setGithubUrl(String githubUrl) {
    this.githubUrl = githubUrl;
  }

  public String getPurl() {
    return purl;
  }

  public void setPurl(String purl) {
    this.purl = purl;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getProjectVersion() {
    return projectVersion;
  }

  public void setProjectVersion(String projectVersion) {
    this.projectVersion = projectVersion;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    Score score = (Score) o;
    return Objects.equals(getId(), score.getId()) && Objects.equals(getProjectName(), score.getProjectName())
        && Objects.equals(getProjectVersion(), score.getProjectVersion())
        && Objects.equals(getValue(), score.getValue()) && Objects.equals(getPurl(), score.getPurl())
        && Objects.equals(getGithubUrl(), score.getGithubUrl())
        && Objects.equals(getMeasurement(), score.getMeasurement());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getProjectName(), getProjectVersion(), getValue(), getPurl(), getGithubUrl(),
        getMeasurement());
  }

  @Override
  public String toString() {
    return "Score{" +
        "id=" + id +
        ", projectName='" + projectName + '\'' +
        ", projectVersion='" + projectVersion + '\'' +
        ", value=" + value +
        ", purl='" + purl + '\'' +
        ", githubUrl='" + githubUrl + '\'' +
        ", measurement=" + measurement +
        '}';
  }
}
