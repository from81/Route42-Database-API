package com.comp6442.route42.repository;

import com.comp6442.route42.model.Post;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PostRepository {
  Post getOne(String postId) throws InterruptedException, ExecutionException;

  List<Post> getAll() throws InterruptedException, ExecutionException;

  //  Post add(Post post) throws InterruptedException, ExecutionException;

  //  Post delete(long id);
}
