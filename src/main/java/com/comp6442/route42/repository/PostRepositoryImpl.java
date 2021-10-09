package com.comp6442.route42.repository;

import com.comp6442.route42.model.Post;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PostRepositoryImpl extends FirestoreRepository<Post> implements PostRepository {
  private static final Logger logger = Logger.getLogger(PostRepositoryImpl.class.getName());
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

  public List<Post> getByUsername(String username) throws ExecutionException, InterruptedException {
    return this.collection.whereEqualTo("userName", username).get().get().toObjects(Post.class);
  }

  public List<Post> getByHashtags(List<String> hashtags)
      throws ExecutionException, InterruptedException {
    logger.log(Level.INFO, hashtags.toString());
    Set<Post> set = new HashSet<>();
    for (String hashtag : hashtags) {
      set.addAll(getByHashtag(hashtag));
    }
    return new ArrayList<>(set);
  }

  public List<Post> getByHashtag(String hashtag) throws ExecutionException, InterruptedException {
    return this.collection
        .whereArrayContains("hashtags", hashtag)
        .get()
        .get()
        .toObjects(Post.class);
  }
}
