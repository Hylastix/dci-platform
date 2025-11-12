/*
 * File: Measurement.java
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

import jakarta.json.bind.annotation.JsonbTransient;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Measurement")
@NamedQuery(name = "Measurement.findAll", query = "SELECT m FROM Measurement m")
public class Measurement implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "vulnerabilityDensity", nullable = false)
  private Double vulnDensity;

  @NotNull
  @Column(name = "securityIssueDensity", nullable = false)
  private Double secDensity;

  @NotNull
  @Column(name = "bugDensity", nullable = false)
  private Double bugDensity;

  @NotNull
  @Column(name = "codeSmellDensity", nullable = false)
  private Double smellDensity;

  @NotNull
  @Column(name = "commentDensity", nullable = false)
  private Double commentDensity;

  @NotNull
  @Column(name = "hasLicense", nullable = false)
  private Double hasLicense;

  @NotNull
  @Column(name = "usesCI", nullable = false)
  private Double usesCI;

  @NotNull
  @Column(name = "codeCoverage", nullable = false)
  private Double codeCoverage;

  @NotNull
  @Column(name = "busFactor", nullable = false)
  private Double busFactor;

  @NotNull
  @Column(name = "releaseFrequency", nullable = false)
  private Double releaseFrequency;

  @NotNull
  @Column(name = "managesDeps", nullable = false)
  private Double managesDeps;

  @NotNull
  @Column(name = "popularity", nullable = false)
  private Double popularity;

  @OneToOne(mappedBy = "measurement")
  @JsonbTransient
  private Score score;

  // @JsonbProperty("scoreId")
  // public Long getScoreAsId() {
  // return score.getId();
  // }
  //
  public Score getScore() {
    return this.score;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getVulnDensity() {
    return vulnDensity;
  }

  public void setVulnDensity(double vulnDensity) {
    this.vulnDensity = vulnDensity;
  }

  public double getSecDensity() {
    return secDensity;
  }

  public void setSecDensity(double secDensity) {
    this.secDensity = secDensity;
  }

  public double getBugDensity() {
    return bugDensity;
  }

  public void setBugDensity(double bugDensity) {
    this.bugDensity = bugDensity;
  }

  public double getSmellDensity() {
    return smellDensity;
  }

  public void setSmellDensity(double smellDensity) {
    this.smellDensity = smellDensity;
  }

  public double getCommentDensity() {
    return commentDensity;
  }

  public void setCommentDensity(double commentDensity) {
    this.commentDensity = commentDensity;
  }

  public double getHasLicense() {
    return hasLicense;
  }

  public void setHasLicense(double hasLicense) {
    this.hasLicense = hasLicense;
  }

  public double getUsesCI() {
    return usesCI;
  }

  public void setUsesCI(double usesCI) {
    this.usesCI = usesCI;
  }

  public double getCodeCoverage() {
    return codeCoverage;
  }

  public void setCodeCoverage(double codeCoverage) {
    this.codeCoverage = codeCoverage;
  }

  public double getBusFactor() {
    return busFactor;
  }

  public void setBusFactor(double busFactor) {
    this.busFactor = busFactor;
  }

  public double getReleaseFrequency() {
    return releaseFrequency;
  }

  public void setReleaseFrequency(double releaseFrequency) {
    this.releaseFrequency = releaseFrequency;
  }

  public double getManagesDeps() {
    return managesDeps;
  }

  public void setManagesDeps(double managesDeps) {
    this.managesDeps = managesDeps;
  }

  public double getPopularity() {
    return popularity;
  }

  public void setPopularity(double popularity) {
    this.popularity = popularity;
  }

  @JsonbTransient
  public Double getScoreValueWeighted() {
    return ((this.secDensity + this.vulnDensity) / 2.0) * 0.277
        + ((this.bugDensity + this.smellDensity) / 2.0) * 0.164
        + this.commentDensity * 0.092
        + this.hasLicense * 0.072
        + ((this.usesCI + this.codeCoverage) / 2.0) * 0.073
        + this.busFactor * 0.140
        + this.releaseFrequency * 0.049
        + this.managesDeps * 0.096
        + this.popularity * 0.037;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    Measurement that = (Measurement) o;
    return Objects.equals(getId(), that.getId()) && Objects.equals(getVulnDensity(), that.getVulnDensity())
        && Objects.equals(getSecDensity(), that.getSecDensity())
        && Objects.equals(getBugDensity(), that.getBugDensity())
        && Objects.equals(getSmellDensity(), that.getSmellDensity())
        && Objects.equals(getCommentDensity(), that.getCommentDensity())
        && Objects.equals(getHasLicense(), that.getHasLicense()) && Objects.equals(getUsesCI(), that.getUsesCI())
        && Objects.equals(getCodeCoverage(), that.getCodeCoverage())
        && Objects.equals(getBusFactor(), that.getBusFactor())
        && Objects.equals(getReleaseFrequency(), that.getReleaseFrequency())
        && Objects.equals(getManagesDeps(), that.getManagesDeps())
        && Objects.equals(getPopularity(), that.getPopularity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getVulnDensity(), getSecDensity(), getBugDensity(), getSmellDensity(),
        getCommentDensity(), getHasLicense(), getUsesCI(), getCodeCoverage(), getBusFactor(), getReleaseFrequency(),
        getManagesDeps(), getPopularity());
  }

  @Override
  public String toString() {
    return "Measurement{" +
        "id=" + id +
        ", vulnDensity=" + vulnDensity +
        ", secDensity=" + secDensity +
        ", bugDensity=" + bugDensity +
        ", smellDensity=" + smellDensity +
        ", commentDensity=" + commentDensity +
        ", hasLicense=" + hasLicense +
        ", usesCI=" + usesCI +
        ", codeCoverage=" + codeCoverage +
        ", busFactor=" + busFactor +
        ", releaseFrequency=" + releaseFrequency +
        ", managesDeps=" + managesDeps +
        ", popularity=" + popularity +
        '}';
  }
}
