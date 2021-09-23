package com.comp6442.route42.service;

import com.comp6442.route42.model.Post;
import com.comp6442.route42.repository.PostRepository;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {
  private ArrayList<Post> posts = new ArrayList<>();

  public Post getOne(String postId) throws InterruptedException, ExecutionException {
    return PostRepository.getInstance().getOne(postId).get().get().toObject(Post.class);
  }

  public ArrayList<Post> getAll() throws InterruptedException, ExecutionException {
    List<QueryDocumentSnapshot> document = PostRepository.getInstance().getAll().getDocuments();
    for (QueryDocumentSnapshot queryDocument : document) {
      posts.add(queryDocument.toObject(Post.class));
    }
    return posts;
  }
}
