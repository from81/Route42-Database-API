package com.comp6442.route42;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class Route42API {
  public static void main(String[] args) {
    System.out.println(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
    SpringApplication.run(Route42API.class, args);
  }

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.setVisibility(
        mapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }
}
