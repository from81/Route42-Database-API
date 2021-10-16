package com.comp6442.route42.geosearch;

import com.comp6442.route42.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * KD_Tree class creates KD-Tree and search the nearest points Reference and explanation for KD-Tree is
 * at below https://rosettacode.org/wiki/K-d_tree#Java
 */
public class KDTree {
  private final KDTreeNode rootNode;
  private List<Pair<KDTreeNode,Double>> pairs = new ArrayList<>();
  private KDTreeNode bestNode = null;
  private double bestDistance = 0;

  public KDTree(KDTreeNode node) {
    this.rootNode = node;
  }

  public List<Pair<KDTreeNode, Double>> findKNearest(int k, KDTreeNode node) {
    return findKNearest(k, node.getLatitude(), node.getLongitude());
  }

  /**
   * This methods searches list of nodes of 1st to k-th closest from the target (lat, lon)
   * It stops search when pairs.size() = k or pairs.size() = nodeCounts
   * Each pair stores from 1st to k-th pair of (bestNodes, bestDistance) from the target of KDTree
   * @param k
   * @param lat
   * @param lon
   * @return List<Pair<KDTreeNode, Double>> pairs
   */

  public List<Pair<KDTreeNode, Double>> findKNearest(int k, double lat, double lon) {
    KDTreeNode target = new KDTreeNode(lat, lon);
    if (rootNode == null) {
      throw new IllegalStateException("Tree is Empty!");
    }

    k = Math.min(k, this.countNodes());
    pairs = new ArrayList<>();
    bestNode = null;
    bestDistance = 0;
    while (pairs.size() < k) {
      searchNearest(rootNode, target, 0);
      pairs.add(new Pair<>(bestNode, bestDistance));
      bestDistance = 0;
      bestNode = null;
    }
    return pairs;
  }

  public List<Pair<KDTreeNode, Double>> findWithinRadius(double radius, KDTreeNode node) {
    return findWithinRadius(radius, node.getLatitude(), node.getLongitude());
  }

  /**
   * This methods searches list of nodes of within radius from the target (lat, lon)
   * It stops search when newest bestDistance is larger than given radius or pairs.size() = nodeCounts
   * Each pair stores from 1st to r-th pair of (bestNodes, bestDistance) within given radius from the target of KDTree
   * @param radius
   * @param lat
   * @param lon
   * @return List<Pair<KDTreeNode, Double>> pairs
   */
  public List<Pair<KDTreeNode, Double>> findWithinRadius(double radius, double lat, double lon) {
    KDTreeNode target = new KDTreeNode(lat, lon);
    if (rootNode == null) {
      throw new IllegalStateException("Tree is Empty!");
    }

    pairs = new ArrayList<>();
    bestNode = null;
    bestDistance = 0;
    searchNearest(rootNode, target, 0);

    if (bestDistance <= radius) {
      pairs.add(new Pair<>(bestNode, bestDistance));
      bestDistance = 0;
      bestNode = null;
    }

    int nodeCounts = this.rootNode.countNodes();
    if (pairs.size() != 0) {
      while (pairs.get(pairs.size() - 1).getDistance() <= (Double) radius
              && pairs.size() < nodeCounts) {
        searchNearest(rootNode, target, 0);
        pairs.add(new Pair<>(bestNode, bestDistance));
        bestDistance = 0;
        bestNode = null;
      }
      pairs.remove(pairs.size()-1);
    }
    return pairs;
  }

  /**
   * This is a helper method used for both findKNearestEXP and findWithinRadiusEXP method
   * The method skips all bestDistances smaller among all distance inside pairs and adds next bestDistances
   * @param root
   * @param target
   * @param index
   */
  private void searchNearest(KDTreeNode root, KDTreeNode target, int index) {
    if (root == null) return;
    double d = root.getDistanceTo(target);
    if (bestNode == null || d < bestDistance) {
      if (pairs.size() != 0) {
        if ((Double) d <= pairs.get(pairs.size() - 1).getDistance()) {
          double diff = root.getCoordValue(index) - target.getCoordValue(index);
          index = (index + 1) % 2;
          searchNearest(diff > 0 ? root.getLeft() : root.getRight(), target, index);
          if (Math.sqrt(diff * diff)
                  >= Math.max(bestDistance, pairs.get(pairs.size() - 1).getDistance())) {
            return;
          }
          searchNearest(diff > 0 ? root.getRight() : root.getLeft(), target, index);
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
    searchNearest(diff > 0 ? root.getLeft() : root.getRight(), target, index);
    if (Math.sqrt(diff * diff) >= bestDistance) {
      return;
    }
    searchNearest(diff > 0 ? root.getRight() : root.getLeft(), target, index);
  }

  public double getBestDistance() {
    return pairs.get(0).getDistance();
  }

  public double getKBestDistance(int index) {
    return pairs.get(index).getDistance();
  }

  public int countNodes() {
    return this.rootNode.countNodes();
  }

  public String toString() {
    return this.rootNode.toString();
  }

  public String display(int tabs) {
    return this.rootNode.display(tabs);
  }

  public static KDTree fromNodes(List<KDTreeNode> nodes) {
    if (nodes.size() == 0) return null;
    KDTreeNode node = KDTreeNode.fromNodes(nodes, 0, nodes.size(), 0);
    return new KDTree(node);
  }

  public static KDTree fromPosts(List<Post> posts) {
    return fromNodes(posts.stream().map(KDTreeNode::fromPost).collect(Collectors.toList()));
  }
}
