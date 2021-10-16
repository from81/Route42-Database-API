package com.comp6442.route42.geosearch;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class KDTreeTest {
  private List<Integer> sizes = Arrays.asList(1, 5, 25, 50, 100);
  private List<KDTree> trees = new ArrayList<>();
  private KDTreeNode target;

  public static KDTreeNode generateKDTreeNode() {
    Random random = new Random();
    KDTreeNode nodes = randomNPoint(random);
    return nodes;
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

  public static KDTree generateKDTree(int points) {
    return generateKDTree(generateNodes(points));
  }

  private static KDTreeNode randomNPoint(Random random) {
    return new KDTreeNode(random.nextDouble(), random.nextDouble());
  }

  @BeforeEach
  public void generateTestData() {
    for (int i=0; i < sizes.size(); i++) {
      this.trees.add(generateKDTree(sizes.get(i)));
    }
    target = generateKDTreeNode();
  }

  @Test
  @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
  public void testCountNodes() {
    for (int i=0; i < trees.size(); i++) {
      Assertions.assertEquals(sizes.get(i), trees.get(i).countNodes());
    }
  }

  @Test
  @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
  public void testFindOneNearest() {
    for (int i=0; i < trees.size(); i++) {
      KDTreeNode nearestNode = trees.get(i).findKNearest(1, target).get(0);
      Assertions.assertEquals(
              nearestNode.getDistanceTo(target),
              trees.get(i).getBestDistance(),
              1e-8,
              "Distance computation incorrect"
      );
    }
  }

  @Test
  @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
  public void testFindMultiNearest() {
    for (int i=0; i < trees.size(); i++) {
      int k = sizes.get(i) / 2;
      List<KDTreeNode> nearestNodes = trees.get(i).findKNearest(k, target);

      for (int j=0; j < k; j++) {
        Assertions.assertEquals(
                nearestNodes.get(j).getDistanceTo(target),
                trees.get(i).getKBestDistance(j),
                1e-8,
                "Distance computation incorrect");
      }
    }
  }

  @Test
  @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
  public void testFindWithinRadius() {
    List<KDTreeNode> nodes = generateNodes(50);
    KDTree tree = generateKDTree(nodes);
    System.out.println(tree.display(0));

    KDTreeNode target = generateKDTreeNode();
    double radius = -1.0f;
    int i = 0;
    System.out.println(" target node : " + target);
    for (KDTreeNode node : nodes) {
      System.out.println(
          " "
              + i
              + "-th node : "
              + node
              + " distance : "
              + node.getDistanceTo(target)
              + " within radius "
              + radius);
      i++;
    }

    List<KDTreeNode> withinRadiusNodes =
        tree.findWithinRadius(radius, target.getLatitude(), target.getLongitude());
    Assertions.assertTrue(withinRadiusNodes.isEmpty(), "Not Empty");

    radius = 30000f;
    i = 0;
    System.out.println(" target node : " + target);
    for (KDTreeNode node : nodes) {
      System.out.println(
          " "
              + i
              + "-th node : "
              + node
              + " distance : "
              + node.getDistanceTo(target)
              + " within radius "
              + radius);
      i++;
    }

    withinRadiusNodes = tree.findWithinRadius(radius, target.getLatitude(), target.getLongitude());
    i = 0;
    for (KDTreeNode withinRadiusNode : withinRadiusNodes) {
      System.out.println(
          " "
              + i
              + "-th within radius node : "
              + withinRadiusNode
              + " distance : "
              + tree.getRBestDistance(i)
              + " within radius "
              + radius);
      Assertions.assertTrue(withinRadiusNode.getDistanceTo(target) <= radius, "Bigger than Radius");
      i++;
    }

    radius = 1000000f;
    target = generateKDTreeNode();
    withinRadiusNodes = tree.findWithinRadius(radius, target.getLatitude(), target.getLongitude());
    i = 0;
    for (KDTreeNode withinRadiusNode : withinRadiusNodes) {
      System.out.println(
          " "
              + i
              + "-th within radius node : "
              + withinRadiusNode
              + " distance : "
              + tree.getRBestDistance(i)
              + " within radius "
              + radius);
      Assertions.assertTrue(withinRadiusNode.getDistanceTo(target) <= radius, "Bigger than Radius");
      i++;
    }
  }
}
