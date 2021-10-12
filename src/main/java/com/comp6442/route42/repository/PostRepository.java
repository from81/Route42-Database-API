package com.comp6442.route42.repository;

import com.comp6442.route42.model.Post;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PostRepository {
  Post getOne(String postId) throws InterruptedException, ExecutionException;

  List<Post> getMany(int n) throws InterruptedException, ExecutionException;
}
