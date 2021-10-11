package com.comp6442.route42.geosearch;

import java.util.*;

/**
 * KD_Tree class creates KD-Tree and search nearest points
 * Reference and explanation for KD-Tree is at below
 * https://rosettacode.org/wiki/K-d_tree#Java
 */

public class KDTree {
    private Node rootNode = null;
    private Node bestNode = null;
    private List<Node> kbestNodes = new ArrayList<>();
    private double bestDistance = 0;
    private List<Double> kbestDistances = new ArrayList<>();

    public KD_Tree(){

    }

    public KD_Tree(Node node){
        this.rootNode = node;
    }

    public KDTree(List<Node> nodes){
        this.rootNode = createTree(nodes, 0, nodes.size(), 0);
    }

    public Node findNearest(Node target){
        if(rootNode == null){
            throw new IllegalStateException("Tree is Empty!");
        }
        bestNode = null;
        bestDistance = 0;
        searchNearest(rootNode, target, 0);
        return bestNode;
    }

    private void searchNearest(Node root, Node target, int index){
        if(root == null){
            return;
        }
        double d = root.getDistanceFrom(target);
        if(bestNode == null || d < bestDistance){
            bestDistance = d;
            bestNode = root;
        }
        if(bestDistance == 0){
            return;
        }
        double diff = root.getCoordValue(index) - target.getCoordValue(index);
        index = (index + 1) % 2;
        searchNearest(diff > 0 ? root.left : root.right, target, index);
        if(Math.sqrt(diff * diff) >= bestDistance){
            return;
        }
        searchNearest(diff > 0 ? root.right : root.left, target, index);
    }

    public List<Node> findkNearest(int k, double lat, double lon){
        Node target = new Node(lat, lon);
        if(rootNode == null){
            throw new IllegalStateException("Tree is Empty!");
        }
        kbestNodes = new ArrayList<>();
        bestNode = null;
        bestDistance = 0;
        int store = 0;
        while(store < k){
            searchkNearest(rootNode, k, store, target, 0);
            kbestDistances.add(bestDistance);
            kbestNodes.add(bestNode);
            bestNode = null;
            bestDistance = 0;
            store++;
        }
        return kbestNodes;
    }

    private void searchkNearest(Node root, int k, int store, Node target, int index){
        if(root == null){
            return;
        }
        double d = root.getDistanceFrom(target);
        if(bestNode == null || d < bestDistance){
            if(kbestDistances.size() != 0){
                if((Double)d <= kbestDistances.get(store-1)){
                    double diff = root.getCoordValue(index) - target.getCoordValue(index);
                    index = (index + 1) % 2;
                    searchkNearest(diff > 0 ? root.left : root.right, k, store, target, index);
                    if(Math.sqrt(diff * diff) >= Math.max(bestDistance,kbestDistances.get(store-1))){
                        return;
                    }
                    searchkNearest(diff > 0 ? root.right : root.left, k, store, target, index);
                }
                else{
                    bestDistance = d;
                    bestNode = root;
                }
            }
            else{
                bestDistance = d;
                bestNode = root;
            }
        }
        if(bestDistance == 0){
            return;
        }
        double diff = root.getCoordValue(index) - target.getCoordValue(index);
        index = (index + 1) % 2;
        searchkNearest(diff > 0 ? root.left : root.right, k, store, target, index);
        if(Math.sqrt(diff * diff) >= bestDistance){
            return;
        }
        searchkNearest(diff > 0 ? root.right : root.left, k, store, target, index);
    }

    public double getBestDistance() {
        return bestDistance;
    }

    public double getkBestDistance(int index) {
        return kbestDistances.get(index);
    }

    private Node createTree(List<Node> nodes, int begin, int end, int index){
        if(end <= begin){
            return null;
        }
        int n = begin + (end - begin)/2;
        Node node = QuickSelect.select(nodes, begin, end - 1, n, new NodeComparator(index));
        index = (index + 1) % 2;
        node.left = createTree(nodes, begin, n, index);
        node.right = createTree(nodes, n+1, end, index);
        return node;
    }

    public String toString(){
        StringBuilder SB = new StringBuilder();
        SB.append(this.rootNode);
        return String.valueOf(SB);
    }

    public String display(int tabs){
        StringBuilder SB = new StringBuilder();
        SB.append(this.rootNode.display(tabs));
        return String.valueOf(SB);
    }

    private static class NodeComparator implements Comparator<Node> {
        private int index;
        private NodeComparator(int index){
            this.index = index;
        }

        @Override
        public int compare(Node node1, Node node2) {
            return Double.compare(node1.getCoordValue(index),node2.getCoordValue(index));
        }
    }

    public static class Node{
        private double[] coords = new double[2];
        private double lat, lon;
        private Node left = null;
        private Node right = null;

        public Node(double[] coords){
            this.coords = coords;
            this.lat = coords[0];
            this.lon = coords[1];
        }

        public Node(double latitude, double longitude){
            this.coords[0] = latitude;
            this.lat = latitude;
            this.coords[1] = longitude;
            this.lon = longitude;
        }

        double[] getCoords(){
            return this.coords;
        }

        double getLatitude() {
            return this.lat;
        }

        double getLongitude() {
            return this.lon;
        }

        double getCoordValue(int index){
            return this.getCoords()[index];
        }

        double getDistanceFrom(Node node){
            double dist = 0;
            for(int i=0; i < this.coords.length; i++){
                double d_each = this.coords[i] - node.coords[i];
                dist += d_each * d_each;
            }
            return Math.sqrt(dist);
        }

        public String toString(){
            StringBuilder SB = new StringBuilder();
            SB.append("(");
            for(int i=0; i < this.coords.length; i++){
                if(i > 0)
                    SB.append(", ");
                SB.append(this.coords[i]);
            }
            SB.append(")");
            return String.valueOf(SB);
        }

        public String display(int tabs) {
            StringBuilder SB = new StringBuilder();
            SB.append(this);
            if(this.left != null){
                SB.append("\n").append("\t".repeat(tabs)).append("├─").append(this.left.display(tabs + 1));
            }
            if(this.right != null) {
                SB.append("\n").append("\t".repeat(tabs)).append("├─").append(this.right.display(tabs + 1));
            }
            return SB.toString();
        }
    }
}
