package com.comp6442.route42.geosearch;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class KDTreeTest {

    @Test
    public void test2DRandom() {
        double[] results = testRandom(3, 2);
        assertEquals(results[0], results[1],1e-8, "Wrong Coordinate");
    }

    public static double[] testRandom(int points, int dimension) {
        Random random = new Random();
        List<KDTree.Node> nodes = new ArrayList<>();
        for(int i=0; i < points; i++){
            nodes.add(randomNPoint(random, dimension));
        }
        KDTree tree = new KDTree(nodes);
        System.out.println(tree.display(0));
        KDTree.Node target = randomNPoint(random, dimension);
        KDTree.Node nearest = tree.findNearest(target);
        System.out.println("print all data : ");
        int i=0;
        double[] bestDistanceResults = new double[2];
        bestDistanceResults[0] = tree.getBestDistance();
        bestDistanceResults[1] = nodes.get(0).getDistanceFrom(target);
        for(KDTree.Node node : nodes){
            System.out.print("data" + i + ": ");
            System.out.print(node);
            double dist = node.getDistanceFrom(target);
            System.out.println(" distance: " + dist);
            if(bestDistanceResults[1] > dist){
                bestDistanceResults[1] = dist;
            }
            i++;
        }
        System.out.println("Random data (" + points + " points) of " + dimension + "-D data : ");
        System.out.println("target: " + target);
        System.out.println("nearest point: " + nearest);
        System.out.println("distance: " + tree.getBestDistance());
        return bestDistanceResults;
    }

    private static KDTree.Node randomNPoint(Random random, int dimension){
        double[] coord = new double[dimension];
        for(int i=0; i < dimension; i++){
            coord[i] = random.nextDouble();
        }
        return new KDTree.Node(coord);
    }
}