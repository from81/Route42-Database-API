package com.comp6442.route42.geosearch;

import org.junit.Test;

import java.util.*;
import java.util.Random;

import static org.junit.Assert.*;

public class KD_TreeTest {

    @Test
    public void test2DRandom() {
        double[] results = testRandom(3, 2);
        assertEquals("Wrong Coordinate", results[0], results[1],1e-8);
    }

    public static double[] testRandom(int points, int dimension) {
        Random random = new Random();
        List<KD_Tree.Node> nodes = new ArrayList<>();
        for(int i=0; i < points; i++){
            nodes.add(randomNPoint(random, dimension));
        }
        KD_Tree tree = new KD_Tree(nodes);
        System.out.println(tree.display(0));
        KD_Tree.Node target = randomNPoint(random, dimension);
        KD_Tree.Node nearest = tree.findNearest(target);
        System.out.println("print all data : ");
        int i=0;
        double[] bestDistanceResults = new double[2];
        bestDistanceResults[0] = tree.getBestDistance();
        bestDistanceResults[1] = nodes.get(0).getDistanceFrom(target);
        for(KD_Tree.Node node : nodes){
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

    private static KD_Tree.Node randomNPoint(Random random, int dimension){
        double[] coord = new double[dimension];
        for(int i=0; i < dimension; i++){
            coord[i] = random.nextDouble();
        }
        return new KD_Tree.Node(coord);
    }
}