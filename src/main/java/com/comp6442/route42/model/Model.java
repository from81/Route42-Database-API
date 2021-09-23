package com.comp6442.route42.model;

import java.util.UUID;

public abstract class Model {
  protected String id;

  public Model() {
    id = UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }
}
