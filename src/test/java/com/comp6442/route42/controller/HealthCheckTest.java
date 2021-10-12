package com.comp6442.route42.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@Import(HealthCheckController.class)
@SpringBootTest(classes = HealthCheckTest.class)
@AutoConfigureMockMvc
public class HealthCheckTest {

  @Autowired private MockMvc mockMvc;

  @Test
  public void testHealthCheck() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(containsString("OK")));
  }
}
