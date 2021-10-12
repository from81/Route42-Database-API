package com.comp6442.route42.model;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PostTest.class)
public class PostTest {
  @Test
  public void testInstantiation() {
    Post post = new Post();
    Assert.assertEquals(0, post.getIsPublic());
  }
}
