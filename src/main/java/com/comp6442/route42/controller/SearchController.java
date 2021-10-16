package com.comp6442.route42.controller;

import com.comp6442.route42.exception.ResourceNotFoundException;
import com.comp6442.route42.geosearch.KDTree;
import com.comp6442.route42.geosearch.KDTreeNode;
import com.comp6442.route42.geosearch.Pair;
import com.comp6442.route42.model.Post;
import com.comp6442.route42.model.QueryString;
import com.comp6442.route42.query.QueryTreeNode;
import com.comp6442.route42.query.Token;
import com.comp6442.route42.query.Tokenizer;
import com.comp6442.route42.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** TODO: use custom defined exception TODO: use logger.log(Level.DEBUG) instead of INFO */
@RestController
@RequestMapping("/search")
public class SearchController {
  private static final Logger logger = Logger.getLogger(SearchController.class.getName());

  @Autowired PostServiceImpl postService;

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<List<Post>> search(@RequestBody QueryString query) {
    logger.log(Level.INFO, String.format("POST/search/:%s", query));

    List<Token> tokens = Tokenizer.tokenizeQuery(query.getQuery());
    tokens.forEach(
        token -> {
          logger.log(Level.INFO, token.toString());
        });
    QueryTreeNode node = QueryTreeNode.fromTokens(tokens);

    if (node != null) {
      logger.log(Level.INFO, node.toString());
      List<Post> posts = processQueryTreeNode(node);
      logger.log(Level.INFO, posts.toString());

      if (posts.size() == 0) {
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        posts = posts.subList(0, Math.min(query.getLimit(), posts.size()));
        return new ResponseEntity<>(posts, HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/knn")
  public ResponseEntity<List<Post>> getKNearest(
          @RequestParam(value = "lat") Double lat,
          @RequestParam(value = "lon") Double lon,
          @RequestParam(defaultValue = "1", value = "k") int k) throws ExecutionException, InterruptedException {
    logger.log(Level.INFO, String.format("GET/search/knn: k=%d lat=%f lon=%f", k, lat, lon));
    KDTree tree = KDTree.fromPosts(postService.getMany(200));
    List<Pair<KDTreeNode,Double>> knnPairs = tree.findKNearestEXP(k, lat, lon);
    List<KDTreeNode> knn = knnPairs.stream().map(Pair<KDTreeNode,Double>::getNode).collect(Collectors.toList());
//    List<KDTreeNode> knn = tree.findKNearest(k, lat, lon);
    List<Post> posts = knn.stream().map(KDTreeNode::getPost).collect(Collectors.toList());
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  private List<Post> processQueryTreeNode(QueryTreeNode node) {
    if (node.getValue() != null) {
      return filter(node.getKey().trim(), node.getValue().trim());
    } else {
      if (node.getKey().equals("AND")) {
        return intersection(
            processQueryTreeNode(node.getLeft()), processQueryTreeNode(node.getRight()));
      } else {
        assert node.getKey().equals("OR");
        return union(processQueryTreeNode(node.getLeft()), processQueryTreeNode(node.getRight()));
      }
    }
  }

  private static List<Post> intersection(List<Post> result1, List<Post> result2) {
    List<Post> intersectElements = result1.stream().filter(result2::contains).collect(Collectors.toList());

    if (!intersectElements.isEmpty()) {
      return intersectElements;
    } else {
      return Collections.emptyList();
    }
  }

  private static List<Post> union(List<Post> result1, List<Post> result2) {
    Set<Post> set = new HashSet<>();
    set.addAll(result1);
    set.addAll(result2);
    return new ArrayList<>(set);
  }

  private List<Post> filter(String field, String value) {
    try {
      switch (field) {
        case "userName":
        case "username":
          return postService.getByUsername(value);
        case "hashtags":
          List<String> hashtags = List.of(value.split(" "));
          hashtags =
              hashtags.stream()
                  .map(
                      tag -> {
                        if (tag.startsWith("#")) {
                          return tag.replaceAll("^#+", "#");
                        } else {
                          return "#" + tag.trim();
                        }
                      })
                  .collect(Collectors.toList());
          return postService.getByHashtags(hashtags);
      }
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
