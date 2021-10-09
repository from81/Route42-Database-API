package com.comp6442.route42.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryString {
  private String query;

  @JsonCreator
  public QueryString(@JsonProperty("query") String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  @Override
  public String toString() {
    return this.query;
  }
}
