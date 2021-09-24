package com.comp6442.route42.service;

import com.comp6442.route42.model.Post;
import com.comp6442.route42.repository.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PostServiceImpl implements PostService {

  @Autowired private PostRepositoryImpl postRepository;

  public Post getOne(String postId) throws InterruptedException, ExecutionException {
    return postRepository.getOne(postId);
  }

  public List<Post> getAll() throws InterruptedException, ExecutionException {
    return postRepository.getAll();
  }
}
