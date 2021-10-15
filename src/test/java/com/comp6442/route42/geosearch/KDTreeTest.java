package com.comp6442.route42.geosearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Random;

public class KDTreeTest {

    @Test
    public void testCountNodes() {
        List<KDTreeNode> nodes = generateNodes(50);
        KDTree tree = generateKDTree(nodes);
        Assertions.assertEquals(50,tree.countNodes());

        nodes = generateNodes(100);
        tree = generateKDTree(nodes);
        Assertions.assertEquals(100,tree.countNodes());

        nodes = generateNodes(1000);
        tree = generateKDTree(nodes);
        Assertions.assertEquals(tree.getNodeCounts(),tree.countNodes());
    }

    @Test
    public void testFindOneNearest() {

        List<KDTreeNode> nodes = generateNodes(50);
        KDTree tree = generateKDTree(nodes);
        System.out.println(tree.display(0));

        KDTreeNode target = generateTarget();
        System.out.println(" target node : " + target);
        int i = 0;
        for(KDTreeNode node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceTo(target));
            i++;
        }

        KDTreeNode nearestNode = tree.findKNearest(1, target).get(0);
        System.out.println(" nearest neighbor node : " + nearestNode + " distance : " + tree.getBestDistance());

        Assertions.assertEquals(nearestNode.getDistanceTo(target), tree.getBestDistance(), 1e-8, "Wrong Coordinate");

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

        nearestNode = tree.findKNearest(1, target).get(0);
        System.out.println(" nearest neighbor node : " + nearestNode + " distance : " + tree.getBestDistance());

        Assertions.assertEquals(nearestNode.getDistanceTo(target), tree.getBestDistance(), 1e-8, "Wrong Coordinate");
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

        List<KDTreeNode> nearestNodes = tree.findKNearest(k_points, target.getLatitude(),target.getLongitude());
        i = 0;
        for(KDTreeNode nearestNode : nearestNodes){
            System.out.println(" " + i + "-th nearest neighbor node : " + nearestNode + " distance : " + tree.getKBestDistance(i));
            Assertions.assertEquals(nearestNode.getDistanceTo(target), tree.getKBestDistance(i), 1e-8, "Wrong Coordinate");
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

        nearestNodes = tree.findKNearest(k_points, target.getLatitude(),target.getLongitude());
        i = 0;
        for(KDTreeNode nearestNode : nearestNodes){
            System.out.println(" " + i + "-th nearest neighbor node : " + nearestNode + " distance : " + tree.getKBestDistance(i));
            Assertions.assertEquals(nearestNode.getDistanceTo(target), tree.getKBestDistance(i), 1e-8, "Wrong Coordinate");
            i++;
        }

        nodes = generateNodes(5);
        tree = generateKDTree(nodes);
        System.out.println(tree.display(0));

        target = generateTarget();
        System.out.println(" target node : " + target);
        i = 0;
        for(KDTreeNode node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceTo(target));
            i++;
        }

        nearestNodes = tree.findKNearest(k_points, target.getLatitude(),target.getLongitude());
        i = 0;
        for(KDTreeNode nearestNode : nearestNodes){
            System.out.println(" " + i + "-th nearest neighbor node : " + nearestNode + " distance : " + tree.getKBestDistance(i));
            Assertions.assertEquals(nearestNode.getDistanceTo(target), tree.getKBestDistance(i), 1e-8, "Wrong Coordinate");
            i++;
        }
    }

    @org.junit.Test
    public void testFindWithinRadius() {
        List<KDTreeNode> nodes = generateNodes(50);
        KDTree tree = generateKDTree(nodes);
        System.out.println(tree.display(0));

        KDTreeNode target = generateTarget();
        double radius = -1.0f;
        int i = 0;
        System.out.println(" target node : " + target);
        for(KDTreeNode node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceTo(target) + " within radius " + radius);
            i++;
        }

        List<KDTreeNode> withinRadiusNodes = tree.findWithinRadius(radius, target.getLatitude(),target.getLongitude());
        Assertions.assertTrue(withinRadiusNodes.isEmpty(), "Not Empty");


        radius = 30000f;
        i = 0;
        System.out.println(" target node : " + target);
        for(KDTreeNode node : nodes){
            System.out.println(" " + i + "-th node : " + node + " distance : " + node.getDistanceTo(target) + " within radius " + radius);
            i++;
        }

        withinRadiusNodes = tree.findWithinRadius(radius, target.getLatitude(),target.getLongitude());
        i = 0;
        for(KDTreeNode withinRadiusNode : withinRadiusNodes){
            System.out.println(" " + i + "-th within radius node : " + withinRadiusNode + " distance : " + tree.getRBestDistance(i) + " within radius " + radius);
            Assertions.assertTrue(withinRadiusNode.getDistanceTo(target) <= radius, "Bigger than Radius");
            i++;
        }

        radius = 1000000f;
        target = generateTarget();
        withinRadiusNodes = tree.findWithinRadius(radius, target.getLatitude(),target.getLongitude());
        i = 0;
        for(KDTreeNode withinRadiusNode : withinRadiusNodes){
            System.out.println(" " + i + "-th within radius node : " + withinRadiusNode + " distance : " + tree.getRBestDistance(i) + " within radius " + radius);
            Assertions.assertTrue(withinRadiusNode.getDistanceTo(target) <= radius, "Bigger than Radius");
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
        KDTree tree = KDTree.fromNodes(nodes);
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