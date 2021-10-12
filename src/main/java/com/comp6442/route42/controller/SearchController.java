package com.comp6442.route42.controller;

import com.comp6442.route42.exception.ResourceNotFoundException;
import com.comp6442.route42.model.Post;
import com.comp6442.route42.model.QueryString;
import com.comp6442.route42.query.QueryTreeNode;
import com.comp6442.route42.query.Token;
import com.comp6442.route42.query.Tokenizer;
import com.comp6442.route42.service.PostServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<List<Post>> search(@RequestBody QueryString query)
      throws InterruptedException, ExecutionException, ResourceNotFoundException {
    logger.log(Level.INFO, String.format("POST/search/:%s", query));

    List<Token> tokens = Tokenizer.tokenizeQuery(query.toString());
    tokens.forEach(
        token -> {
          logger.log(Level.INFO, token.toString());
        });
    QueryTreeNode node = QueryTreeNode.fromTokens(tokens);

    if (node != null) {
      logger.log(Level.INFO, node.toString());
      List<Post> posts = processQueryTreeNode(node);
      logger.log(Level.INFO, posts.toString());

      if (posts.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
      else return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    List<Post> intersectElements =
        result1.stream().filter(result2::contains).collect(Collectors.toList());

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
