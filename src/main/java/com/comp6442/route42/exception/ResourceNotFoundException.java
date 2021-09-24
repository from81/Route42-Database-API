package com.comp6442.route42.exception;

public class ResourceNotFoundException extends Exception{
  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String message) {
    super(message);
  }
}