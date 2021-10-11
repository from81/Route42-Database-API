package com.comp6442.route42.geosearch;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class KDTreeTest {

    @Test
    public void testFindOneNearest() {

        List<KD_Tree.Node> nodes = generateNodes(3);
        KD_Tree tree = generateKD_Tree(nodes);
        System.out.println(tree.display(0));

        KD_Tree.Node target = generateTarget();
        System.out.println(" target node : " + target);
        int i = 0;
        for(KD_Tree.Node node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceFrom(target));
            i++;
        }

        KD_Tree.Node nearest_node = tree.findNearest(target.getLatitude(),target.getLongitude());
        System.out.println(" nearest neighbor node : " + nearest_node + " distance : " + tree.getBestDistance());

        assertEquals("Wrong Coordinate", nearest_node.getDistanceFrom(target), tree.getBestDistance(), 1e-8);

        nodes = generateNodes(7);
        tree = generateKD_Tree(nodes);
        System.out.println(tree.display(0));

        target = generateTarget();
        System.out.println(" target node : " + target);
        i = 0;
        for(KD_Tree.Node node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceFrom(target));
            i++;
        }

        nearest_node = tree.findNearest(target.getLatitude(),target.getLongitude());
        System.out.println(" nearest neighbor node : " + nearest_node + " distance : " + tree.getBestDistance());

        assertEquals("Wrong Coordinate", nearest_node.getDistanceFrom(target), tree.getBestDistance(), 1e-8);
    }

    @Test
    public void testFindMultiNearest() {
        int k_points = 10;
        List<KD_Tree.Node> nodes = generateNodes(50);
        KD_Tree tree = generateKD_Tree(nodes);
        System.out.println(tree.display(0));

        KD_Tree.Node target = generateTarget();
        int i = 0;
        System.out.println(" target node : " + target);
        for(KD_Tree.Node node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceFrom(target));
            i++;
        }

        List<KD_Tree.Node> nearest_nodes = tree.findkNearest(k_points, target.getLatitude(),target.getLongitude());
        i = 0;
        for(KD_Tree.Node nearest_node : nearest_nodes){
            System.out.println(" " + i + "-th nearest neighbor node : " + nearest_node + " distance : " + tree.getkBestDistance(i));
            assertEquals("Wrong Coordinate", nearest_node.getDistanceFrom(target), tree.getkBestDistance(i), 1e-8);
            i++;
        }

        nodes = generateNodes(100);
        tree = generateKD_Tree(nodes);
        System.out.println(tree.display(0));

        target = generateTarget();
        System.out.println(" target node : " + target);
        i = 0;
        for(KD_Tree.Node node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceFrom(target));
            i++;
        }

        nearest_nodes = tree.findkNearest(k_points, target.getLatitude(),target.getLongitude());
        i = 0;
        for(KD_Tree.Node nearest_node : nearest_nodes){
            System.out.println(" " + i + "-th nearest neighbor node : " + nearest_node + " distance : " + tree.getkBestDistance(i));
            assertEquals("Wrong Coordinate", nearest_node.getDistanceFrom(target), tree.getkBestDistance(i), 1e-8);
            i++;
        }
    }

    public static List<KD_Tree.Node> generateNodes(int points) {
        Random random = new Random();
        List<KD_Tree.Node> nodes = new ArrayList<>();
        for(int i=0; i < points; i++){
            nodes.add(randomNPoint(random));
        }
        return nodes;
    }

    public static KD_Tree generateKD_Tree(List<KD_Tree.Node> nodes) {
        KD_Tree tree = new KD_Tree(nodes);
        return tree;
    }

    public static KD_Tree.Node generateTarget() {
        Random random = new Random();
        KD_Tree.Node nodes = randomNPoint(random);
        return nodes;
    }

    private static KD_Tree.Node randomNPoint(Random random){
        double[] coord = new double[2];
        coord[0] = random.nextDouble();
        coord[1] = random.nextDouble();
        return new KD_Tree.Node(coord);
    }
}