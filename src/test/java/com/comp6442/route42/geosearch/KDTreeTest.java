package com.comp6442.route42.geosearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Random;

public class KDTreeTest {

    @Test
    public void testFindOneNearest() {

        List<KDTreeNode> nodes = generateNodes(3);
        KDTree tree = generateKDTree(nodes);
        System.out.println(tree.display(0));

        KDTreeNode target = generateTarget();
        System.out.println(" target node : " + target);
        int i = 0;
        for(KDTreeNode node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceTo(target));
            i++;
        }

        KDTreeNode nearest_node = tree.findNearest(target);
        System.out.println(" nearest neighbor node : " + nearest_node + " distance : " + tree.getBestDistance());

        Assertions.assertEquals(nearest_node.getDistanceTo(target), tree.getBestDistance(), 1e-8, "Wrong Coordinate");

        nodes = generateNodes(7);
        tree = generateKDTree(nodes);
        System.out.println(tree.display(0));

        target = generateTarget();
        System.out.println(" target node : " + target);
        i = 0;
        for(KDTreeNode node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceTo(target));
            i++;
        }

        nearest_node = tree.findNearest(target);
        System.out.println(" nearest neighbor node : " + nearest_node + " distance : " + tree.getBestDistance());

        Assertions.assertEquals(nearest_node.getDistanceTo(target), tree.getBestDistance(), 1e-8, "Wrong Coordinate");
    }

    @Test
    public void testFindMultiNearest() {
        int k_points = 10;
        List<KDTreeNode> nodes = generateNodes(50);
        KDTree tree = generateKDTree(nodes);
        System.out.println(tree.display(0));

        KDTreeNode target = generateTarget();
        int i = 0;
        System.out.println(" target node : " + target);
        for(KDTreeNode node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceTo(target));
            i++;
        }

        List<KDTreeNode> nearest_nodes = tree.findKNearest(k_points, target.getLatitude(),target.getLongitude());
        i = 0;
        for(KDTreeNode nearest_node : nearest_nodes){
            System.out.println(" " + i + "-th nearest neighbor node : " + nearest_node + " distance : " + tree.getKBestDistance(i));
            Assertions.assertEquals(nearest_node.getDistanceTo(target), tree.getKBestDistance(i), 1e-8, "Wrong Coordinate");
            i++;
        }

        nodes = generateNodes(100);
        tree = generateKDTree(nodes);
        System.out.println(tree.display(0));

        target = generateTarget();
        System.out.println(" target node : " + target);
        i = 0;
        for(KDTreeNode node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceTo(target));
            i++;
        }

        nearest_nodes = tree.findKNearest(k_points, target.getLatitude(),target.getLongitude());
        i = 0;
        for(KDTreeNode nearest_node : nearest_nodes){
            System.out.println(" " + i + "-th nearest neighbor node : " + nearest_node + " distance : " + tree.getKBestDistance(i));
            Assertions.assertEquals(nearest_node.getDistanceTo(target), tree.getKBestDistance(i), 1e-8, "Wrong Coordinate");
            i++;
        }
    }

    public static List<KDTreeNode> generateNodes(int points) {
        Random random = new Random();
        List<KDTreeNode> nodes = new ArrayList<>();
        for(int i=0; i < points; i++){
            nodes.add(randomNPoint(random));
        }
        return nodes;
    }

    public static KDTree generateKDTree(List<KDTreeNode> nodes) {
        KDTree tree = KDTree.fromNodes(nodes);;
        return tree;
    }

    public static KDTreeNode generateTarget() {
        Random random = new Random();
        KDTreeNode nodes = randomNPoint(random);
        return nodes;
    }

    private static KDTreeNode randomNPoint(Random random){
        return new KDTreeNode(random.nextDouble(), random.nextDouble());
    }
}