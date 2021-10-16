package com.comp6442.route42.geosearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KDTreeTest {

  @Test
  public void testCountNodes() {
    List<KDTreeNode> nodes = generateNodes(50);
    KDTree tree = generateKDTree(nodes);
    Assertions.assertEquals(50, tree.countNodes());

    nodes = generateNodes(100);
    tree = generateKDTree(nodes);
    Assertions.assertEquals(100, tree.countNodes());

    nodes = generateNodes(1000);
    tree = generateKDTree(nodes);
    Assertions.assertEquals(1000, tree.countNodes());
  }

  @Test
  public void testFindOneNearest() {
    List<KDTreeNode> nodes = generateNodes(50);
    KDTree tree = generateKDTree(nodes);
    KDTreeNode target = generateTarget();
    KDTreeNode nearestNode = tree.findKNearest(1, target).get(0);
    Assertions.assertEquals(
        nearestNode.getDistanceTo(target), tree.getBestDistance(), 1e-8, "Wrong Coordinate");

    nodes = generateNodes(100);
    tree = generateKDTree(nodes);
    target = generateTarget();
    nearestNode = tree.findKNearest(1, target).get(0);
    Assertions.assertEquals(
        nearestNode.getDistanceTo(target), tree.getBestDistance(), 1e-8, "Wrong Coordinate");
  }

  @Test
  public void testFindMultiNearest() {
    int k_points = 10;

    // find 10 nearest points of KD Tree with 50 nodes
    List<KDTreeNode> nodes = generateNodes(50);
    KDTree tree = generateKDTree(nodes);
    KDTreeNode target = generateTarget();
    List<KDTreeNode> nearestNodes =
        tree.findKNearest(k_points, target.getLatitude(), target.getLongitude());
    int i = 0;
    for (KDTreeNode nearestNode : nearestNodes) {
      Assertions.assertEquals(
          nearestNode.getDistanceTo(target), tree.getKBestDistance(i), 1e-8, "Wrong Coordinate");
      i++;
    }

    // find 10 nearest points of KD Tree with 100 nodes
    nodes = generateNodes(100);
    tree = generateKDTree(nodes);
    target = generateTarget();
    nearestNodes = tree.findKNearest(k_points, target.getLatitude(), target.getLongitude());
    i = 0;
    for (KDTreeNode nearestNode : nearestNodes) {
      Assertions.assertEquals(
          nearestNode.getDistanceTo(target), tree.getKBestDistance(i), 1e-8, "Wrong Coordinate");
      i++;
    }

    // find 10 nearest points of KD Tree with 5 nodes
    nodes = generateNodes(5);
    tree = generateKDTree(nodes);
    target = generateTarget();
    nearestNodes = tree.findKNearest(k_points, target.getLatitude(), target.getLongitude());
    i = 0;
    for (KDTreeNode nearestNode : nearestNodes) {
      Assertions.assertEquals(
          nearestNode.getDistanceTo(target), tree.getKBestDistance(i), 1e-8, "Wrong Coordinate");
      i++;
    }
  }

  @org.junit.Test
  public void testFindWithinRadius() {
    // Create KDTree with 50 nodes
    List<KDTreeNode> nodes = generateNodes(50);
    KDTree tree = generateKDTree(nodes);
    KDTreeNode target = generateTarget();

    // Test empty set
    double radius = -1.0f;
    List<KDTreeNode> withinRadiusNodes =
        tree.findWithinRadius(radius, target.getLatitude(), target.getLongitude());
    Assertions.assertTrue(withinRadiusNodes.isEmpty(), "Not Empty");

    // Test radius 30000
    radius = 30000f;
    int i = 0;
    withinRadiusNodes = tree.findWithinRadius(radius, target.getLatitude(), target.getLongitude());
    for (KDTreeNode withinRadiusNode : withinRadiusNodes) {
      Assertions.assertTrue(withinRadiusNode.getDistanceTo(target) <= radius, "Bigger than Radius");
      i++;
    }

    // Test all nodes within radius
    radius = 1000000f;
    target = generateTarget();
    withinRadiusNodes = tree.findWithinRadius(radius, target.getLatitude(), target.getLongitude());
    i = 0;
    for (KDTreeNode withinRadiusNode : withinRadiusNodes) {
      Assertions.assertTrue(withinRadiusNode.getDistanceTo(target) <= radius, "Bigger than Radius");
      i++;
    }
  }

  public static List<KDTreeNode> generateNodes(int points) {
    Random random = new Random();
    List<KDTreeNode> nodes = new ArrayList<>();
    for (int i = 0; i < points; i++) {
      nodes.add(randomNPoint(random));
    }
    return nodes;
  }

  public static KDTree generateKDTree(List<KDTreeNode> nodes) {
    KDTree tree = KDTree.fromNodes(nodes);
    return tree;
  }

  public static KDTreeNode generateTarget() {
    Random random = new Random();
    KDTreeNode nodes = randomNPoint(random);
    return nodes;
  }

  private static KDTreeNode randomNPoint(Random random) {
    return new KDTreeNode(random.nextDouble(), random.nextDouble());
  }
}
