package com.comp6442.route42.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class HealthCheckController {
  private static final Logger logger = Logger.getLogger(HealthCheckController.class.getName());

  @GetMapping("/")
  public ResponseEntity<String> healthCheck(HttpEntity<byte[]> requestEntity) {
    String requestHeader = requestEntity.getHeaders().toString();
    logger.log(Level.INFO, String.format("GET/: %s", requestEntity));
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Received header", requestHeader);
    return new ResponseEntity<>("OK\n", responseHeaders, HttpStatus.OK);
  }
}
