package com.comp6442.route42.controller;

import com.comp6442.route42.exception.ResourceNotFoundException;
import com.comp6442.route42.model.Post;
import com.comp6442.route42.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/post")
public class PostController {
  @Autowired PostService postService;

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable String id)
      throws InterruptedException, ExecutionException, ResourceNotFoundException {
    Post post = postService.getOne(id);
    if (post == null) throw new ResourceNotFoundException("Post not Found");
    return new ResponseEntity<>(post, org.springframework.http.HttpStatus.OK);
  }

  @GetMapping("/")
  public ResponseEntity<ArrayList<Post>> getPosts()
      throws InterruptedException, ExecutionException {
    ArrayList<Post> posts = postService.getAll();
    return new ResponseEntity<>(posts, org.springframework.http.HttpStatus.OK);
  }
}
