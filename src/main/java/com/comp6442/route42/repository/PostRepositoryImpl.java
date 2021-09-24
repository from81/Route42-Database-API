package com.comp6442.route42.repository;

import com.comp6442.route42.model.Post;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PostRepositoryImpl extends FirestoreRepository<Post> implements PostRepository {
  @Autowired private Firestore firestore;

  private static PostRepositoryImpl instance = null;

  private PostRepositoryImpl() {
    super("posts", Post.class);
  }

  public static PostRepositoryImpl getInstance() {
    if (PostRepositoryImpl.instance == null) {
      PostRepositoryImpl.instance = new PostRepositoryImpl();
    }
    return PostRepositoryImpl.instance;
  }

  public Post getOne(String postId) throws ExecutionException, InterruptedException {
    return this.collection.document(postId).get().get().toObject(Post.class);
  }

  public List<Post> getAll() throws ExecutionException, InterruptedException {
    return this.collection.get().get().toObjects(Post.class);
  }
}
