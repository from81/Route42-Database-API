package com.comp6442.route42.repository;

import com.comp6442.route42.model.Post;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class PostRepository extends FirestoreRepository<Post> {
  private static PostRepository instance = null;

  private PostRepository() {
    super("posts", Post.class);
  }

  public static PostRepository getInstance() {
    if (PostRepository.instance == null) {
      PostRepository.instance = new PostRepository();
    }
    return PostRepository.instance;
  }

  public DocumentReference getOne(String postId) {
    return this.collection.document(postId);
  }

  public QuerySnapshot getAll() throws ExecutionException, InterruptedException {
    return this.collection.get().get();
  }
}
