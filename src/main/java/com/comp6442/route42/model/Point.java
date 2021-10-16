package com.comp6442.route42.model;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;

import java.io.Serializable;


@IgnoreExtraProperties
public class Point implements Serializable {

  private Double latitude;
  private Double longitude;

  public Point() {
  }

  public Point(Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  @Override
  public String toString() {
    return "Point{" +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
  }
}
