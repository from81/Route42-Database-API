package com.comp6442.route42.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryString {
  private String query;
  private final int limit;

  @JsonCreator
  public QueryString(@JsonProperty("query") String query, @JsonProperty("limit") int limit) {
    this.query = query;
    this.limit = limit;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public int getLimit() {
    return limit;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("QueryString{");
    sb.append("query='").append(query).append('\'');
    sb.append(", limit=").append(limit);
    sb.append('}');
    return sb.toString();
  }
}
