package com.comp6442.route42.geosearch;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class KDTreeTest {
  private List<Integer> sizes = Arrays.asList(1, 5, 25, 50, 100);
  private List<Double> radii = Arrays.asList(-1.0, 30000.0, 100000.0);
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
//      KDTreeNode nearestNode = trees.get(i).findKNearest(1, target).get(0);
//      Assertions.assertEquals(
//              nearestNode.getDistanceTo(target),
//              trees.get(i).getBestDistance(),
//              1e-8,
//              "Distance computation incorrect"
//      );

      Pair<KDTreeNode, Double> pairNode = trees.get(i).findKNearestEXP(1, target).get(0);
      Assertions.assertEquals(
              pairNode.getNode().getDistanceTo(target),
              trees.get(i).getBestDistanceEXP(),
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
//      List<KDTreeNode> nearestNodes = trees.get(i).findKNearest(k, target);
//
//      for (int j=0; j < k; j++) {
//        Assertions.assertEquals(
//                nearestNodes.get(j).getDistanceTo(target),
//                trees.get(i).getKBestDistance(j),
//                1e-8,
//                "Distance computation incorrect");
//      }

      List<Pair<KDTreeNode, Double>> pairNodes = trees.get(i).findKNearestEXP(k, target);

      for (int j=0; j < k; j++) {
        Assertions.assertEquals(
                pairNodes.get(j).getNode().getDistanceTo(target),
                trees.get(i).getKBestDistanceEXP(j),
                1e-8,
                "Distance computation incorrect");
      }
    }
  }

  @Test
  @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
  public void testFindWithinRadius() {
    for (int i=0; i < trees.size(); i++) {
      for (int j=0; j < radii.size(); j++) {
//        List<KDTreeNode> nearestNodes = trees.get(i).findWithinRadius(radii.get(j), target);
//
//        // Distance calculation test
//        for (int k = 0; k < nearestNodes.size(); k++) {
//          Assertions.assertTrue(
//                  nearestNodes.get(k).getDistanceTo(target) <= radii.get(j),
//                  "Distance larger than radius");
//        }

        List<Pair<KDTreeNode, Double>> pairNodes = trees.get(i).findWithinRadiusEXP(radii.get(j), target);

        // Distance calculation test
        for (int k = 0; k < pairNodes.size(); k++) {
          Assertions.assertTrue(
                  pairNodes.get(k).getNode().getDistanceTo(target) <= radii.get(j),
                  "Distance larger than radius");
        }
      }
    }
  }
}
