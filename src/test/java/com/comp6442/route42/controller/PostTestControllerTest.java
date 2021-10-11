package com.comp6442.route42.controller;

import com.comp6442.route42.exception.ResourceNotFoundException;
import com.comp6442.route42.model.Post;
import com.comp6442.route42.repository.PostRepositoryImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = PostTestControllerTest.class)
public class PostTestControllerTest {
  @Autowired private PostRepositoryImpl postRepository;
  @Autowired private PostController postController;

  @Test
  public void contextLoads() {}

  @Test
  public void testGetPostById() {
    try {
      String postid = "0092827e-2961-48ed-8995-50adb9f47781";
      ResponseEntity<Post> response = postController.getPostById("0092827e-2961-48ed-8995-50adb9f47781");
//      assertEquals(HttpStatus.OK, response.getStatusCode());
//      assertEquals(postid, response.getBody().getId());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (ResourceNotFoundException e) {
      e.printStackTrace();
    }

  }
}