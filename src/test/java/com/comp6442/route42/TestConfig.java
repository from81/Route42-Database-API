package com.comp6442.route42;

import com.comp6442.route42.repository.PostRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.comp6442.route42")
public class TestConfig {

  @Bean
  public PostRepositoryImpl postRepository() {
    return PostRepositoryImpl.getInstance();
  }
}
