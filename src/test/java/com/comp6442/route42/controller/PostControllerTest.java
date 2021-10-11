package com.comp6442.route42.controller;

import com.comp6442.route42.model.Post;
import com.comp6442.route42.repository.PostRepositoryImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = PostControllerTest.class)
public class PostControllerTest {
  @Autowired private PostRepositoryImpl postRepository;

  @Test
  public void contextLoads() {}

  @Test
  public void initialTest() {
    try {
      String postid = "0092827e-2961-48ed-8995-50adb9f47781";
      Post post = PostRepositoryImpl.getInstance().getOne(postid);
      System.out.println(post);
      assertEquals(postid, post.getId());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

  }
}