package com.comp6442.route42.geosearch;

import com.comp6442.route42.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * KD_Tree class creates KD-Tree and search nearest points Reference and explanation for KD-Tree is
 * at below https://rosettacode.org/wiki/K-d_tree#Java
 */
public class KDTree {
  private final List<Double> kbestDistances = new ArrayList<>();
  private final KDTreeNode rootNode;
  private List<KDTreeNode> kbestNodes = new ArrayList<>();
  private KDTreeNode bestNode = null;
  private double bestDistance = 0;

  public KDTree(KDTreeNode node) {
    this.rootNode = node;
  }

  public List<KDTreeNode> findKNearest(int k, KDTreeNode node) {
    return findKNearest(k, node.getLatitude(), node.getLongitude());
  }

  public List<KDTreeNode> findKNearest(int k, double lat, double lon) {
    KDTreeNode target = new KDTreeNode(lat, lon);
    if (rootNode == null) {
      throw new IllegalStateException("Tree is Empty!");
    }
    kbestNodes = new ArrayList<>();
    bestNode = null;
    bestDistance = 0;
    while (kbestNodes.size() < k) {
      searchKNearest(rootNode, target, 0);
      kbestDistances.add(bestDistance);
      kbestNodes.add(bestNode);
      bestDistance = 0;
      bestNode = null;
    }
    return kbestNodes;
  }

  private void searchKNearest(KDTreeNode root, KDTreeNode target, int index) {
    if (root == null) return;
    double d = root.getDistanceTo(target);
    if (bestNode == null || d < bestDistance) {
      if (kbestDistances.size() != 0) {
        if ((Double) d <= kbestDistances.get(kbestNodes.size() - 1)) {
          double diff = root.getCoordValue(index) - target.getCoordValue(index);
          index = (index + 1) % 2;
          searchKNearest(diff > 0 ? root.getLeft() : root.getRight(), target, index);
          if (Math.sqrt(diff * diff) >= Math.max(bestDistance, kbestDistances.get(kbestNodes.size() - 1))) {
            return;
          }
          searchKNearest(diff > 0 ? root.getRight() : root.getLeft(), target, index);
          return;
        } else {
          bestDistance = d;
          bestNode = root;
        }
      } else {
        bestDistance = d;
        bestNode = root;
      }
    }
    if (bestDistance == 0) {
      return;
    }
    double diff = root.getCoordValue(index) - target.getCoordValue(index);
    index = (index + 1) % 2;
    searchKNearest(diff > 0 ? root.getLeft() : root.getRight(), target, index);
    if (Math.sqrt(diff * diff) >= bestDistance) {
      return;
    }
    searchKNearest(diff > 0 ? root.getRight() : root.getLeft(), target, index);
  }

  public double getBestDistance() {
    return kbestDistances.get(0);
  }

  public double getKBestDistance(int index) {
    return kbestDistances.get(index);
  }

  public static KDTree fromNodes(List<KDTreeNode> nodes) {
    if (nodes.size() == 0) return null;
    KDTreeNode node = KDTreeNode.fromNodes(nodes, 0, nodes.size(), 0);
    return new KDTree(node);
  }

  public static KDTree fromPosts(List<Post> posts){
    return fromNodes(posts.stream().map(KDTreeNode::fromPost).collect(Collectors.toList()));
  }

  public String toString() {
    return this.rootNode.toString();
  }

  public String display(int tabs) {
    return this.rootNode.display(tabs);
  }
}
