package com.comp6442.route42.controller;

import com.comp6442.route42.exception.ResourceNotFoundException;
import com.comp6442.route42.model.Post;
import com.comp6442.route42.service.PostServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/post")
public class PostController {
  private static final Logger logger = Logger.getLogger(PostController.class.getName());

  @Autowired PostServiceImpl postService;

  @Autowired ObjectMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable String id)
      throws InterruptedException, ExecutionException, ResourceNotFoundException {
    logger.log(Level.INFO, String.format("GET/post/{id}: %s", id));
    Post post = postService.getOne(id);
    if (post == null) throw new ResourceNotFoundException("Post not Found");
    logger.log(Level.INFO, post.toString());
    return new ResponseEntity<>(post, org.springframework.http.HttpStatus.OK);
  }

  @GetMapping("/")
  public ResponseEntity<List<Post>> getPosts() throws InterruptedException, ExecutionException {
    List<Post> posts = postService.getAll();
    return new ResponseEntity<>(posts, org.springframework.http.HttpStatus.OK);
  }
}
