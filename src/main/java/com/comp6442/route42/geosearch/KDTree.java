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
  private final List<Double> kbestDistances = new ArrayList<>();
  private List<Double> rbestDistances = new ArrayList<>();
  private final KDTreeNode rootNode;
  private List<KDTreeNode> kbestNodes = new ArrayList<>();
  private List<KDTreeNode> rbestNodes = new ArrayList<>();
  private List<Pair<KDTreeNode,Double>> pairs = new ArrayList<>();
  private KDTreeNode bestNode = null;
  private double bestDistance = 0;

  public KDTree(KDTreeNode node) {
    this.rootNode = node;
  }

  public List<KDTreeNode> findKNearest(int k, KDTreeNode node) {
    return findKNearest(k, node.getLatitude(), node.getLongitude());
  }

  /**
   * This methods searches list of nodes of 1st to k-th closest from the target (lat, lon)
   * It stops search when kbestNodes.size() = k or kbestNodes.size() = nodeCounts
   * kbestDistances stores from 1st to k-th bestDistance from the target of KDTree
   * kbestNodes stores from 1st to k-th bestNodes from the target of KDTree
   * @param k
   * @param lat
   * @param lon
   * @return List<KDTreeNode> kbestNodes
   */
  public List<KDTreeNode> findKNearest(int k, double lat, double lon) {
    KDTreeNode target = new KDTreeNode(lat, lon);
    if (rootNode == null) {
      throw new IllegalStateException("Tree is Empty!");
    }

    k = Math.min(k, this.countNodes());
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

  /**
   * This is a helper method used for findKNearest method
   * searchKNearest method skips all bestDistances smaller than kbestDistances and adds next bestDistances
   * @param root
   * @param target
   * @param index
   */
  private void searchKNearest(KDTreeNode root, KDTreeNode target, int index) {
    if (root == null) return;
    double d = root.getDistanceTo(target);
    if (bestNode == null || d < bestDistance) {
      if (kbestDistances.size() != 0) {
        if ((Double) d <= kbestDistances.get(kbestNodes.size() - 1)) {
          double diff = root.getCoordValue(index) - target.getCoordValue(index);
          index = (index + 1) % 2;
          searchKNearest(diff > 0 ? root.getLeft() : root.getRight(), target, index);
          if (Math.sqrt(diff * diff)
              >= Math.max(bestDistance, kbestDistances.get(kbestNodes.size() - 1))) {
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

  public List<Pair<KDTreeNode, Double>> findKNearestEXP(int k, KDTreeNode node) {
    return findKNearestEXP(k, node.getLatitude(), node.getLongitude());
  }

  public List<Pair<KDTreeNode, Double>> findKNearestEXP(int k, double lat, double lon) {
    KDTreeNode target = new KDTreeNode(lat, lon);
    if (rootNode == null) {
      throw new IllegalStateException("Tree is Empty!");
    }

    k = Math.min(k, this.countNodes());
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
    return pairs;
  }

  private void searchKNearestEXP(KDTreeNode root, KDTreeNode target, int index) {
    if (root == null) return;
    double d = root.getDistanceTo(target);
    if (bestNode == null || d < bestDistance) {
      if (kbestDistances.size() != 0) {
        if ((Double) d <= kbestDistances.get(kbestNodes.size() - 1)) {
          double diff = root.getCoordValue(index) - target.getCoordValue(index);
          index = (index + 1) % 2;
          searchKNearest(diff > 0 ? root.getLeft() : root.getRight(), target, index);
          if (Math.sqrt(diff * diff)
                  >= Math.max(bestDistance, kbestDistances.get(kbestNodes.size() - 1))) {
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

  public List<KDTreeNode> findWithinRadius(double radius, KDTreeNode node) {
    return findWithinRadius(radius, node.getLatitude(), node.getLongitude());
  }

  /**
   * This methods searches list of nodes of within radius from the target (lat, lon)
   * It stops search when newest bestDistance is larger than given radius or rbestNodes.size() = nodeCounts
   * rbestDistances stores from 1st to r-th bestDistance where all distances are within given radius from the target of KDTree
   * rbestNodes stores from 1st to r-th bestNodes where all nodes are within given radius from the target of KDTree
   * @param radius
   * @param lat
   * @param lon
   * @return List<KDTreeNode> rbestNodes
   */
  public List<KDTreeNode> findWithinRadius(double radius, double lat, double lon) {
    KDTreeNode target = new KDTreeNode(lat, lon);
    if (rootNode == null) {
      throw new IllegalStateException("Tree is Empty!");
    }

    rbestNodes = new ArrayList<>();
    rbestDistances = new ArrayList<>();
    bestNode = null;
    bestDistance = 0;
    searchRNearest(rootNode, target, 0);

    if (bestDistance <= radius) {
      rbestDistances.add(bestDistance);
      rbestNodes.add(bestNode);
      bestDistance = 0;
      bestNode = null;
    }

    int nodeCounts = this.rootNode.countNodes();
    if (rbestNodes.size() != 0) {
      while (rbestDistances.get(rbestDistances.size() - 1) <= (Double) radius
          && rbestNodes.size() < nodeCounts) {
        searchRNearest(rootNode, target, 0);
        rbestDistances.add(bestDistance);
        rbestNodes.add(bestNode);
        bestDistance = 0;
        bestNode = null;
      }
      rbestDistances.remove(rbestDistances.size() - 1);
      rbestNodes.remove(rbestNodes.size() - 1);
    }
    return rbestNodes;
  }

  /**
   * This is a helper method used for findWithinRadius method
   * searchRNearest method skips all bestDistances smaller than rbestDistances and adds next bestDistances
   * @param root
   * @param target
   * @param index
   */
  private void searchRNearest(KDTreeNode root, KDTreeNode target, int index) {
    if (root == null) return;
    double d = root.getDistanceTo(target);
    if (bestNode == null || d < bestDistance) {
      if (rbestDistances.size() != 0) {
        if ((Double) d <= rbestDistances.get(rbestNodes.size() - 1)) {
          double diff = root.getCoordValue(index) - target.getCoordValue(index);
          index = (index + 1) % 2;
          searchRNearest(diff > 0 ? root.getLeft() : root.getRight(), target, index);
          if (Math.sqrt(diff * diff)
              >= Math.max(bestDistance, rbestDistances.get(rbestNodes.size() - 1))) {
            return;
          }
          searchRNearest(diff > 0 ? root.getRight() : root.getLeft(), target, index);
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
    searchRNearest(diff > 0 ? root.getLeft() : root.getRight(), target, index);
    if (Math.sqrt(diff * diff) >= bestDistance) {
      return;
    }
    searchRNearest(diff > 0 ? root.getRight() : root.getLeft(), target, index);
  }

  public double getBestDistance() {
    return kbestDistances.get(0);
  }

  public double getKBestDistance(int index) {
    return kbestDistances.get(index);
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
