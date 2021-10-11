package com.comp6442.route42.geosearch;

import java.util.*;

public class KD_Tree {
    private Node rootNode = null;
    private Node bestNode = null;
    private double bestDistance = 0;

    public KD_Tree(List<Node> nodes){
        this.rootNode = createTree(nodes, 0, nodes.size(), 0);
    }

    public Node findNearest(Node target){
        System.out.println(" findNearest : target " + target);
        if(rootNode == null){
            throw new IllegalStateException("Tree is Empty!");
        }
        bestNode = null;
        bestDistance = 0;
        searchNearest(rootNode, target, 0);
        return bestNode;
    }

    private void searchNearest(Node root, Node target, int index){
        System.out.println(" searchNearest : root " + root + " target " + target + " at index=" + index);
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
        System.out.println(" distance d=" + d + " best distance=" + bestDistance);
        double diff = root.getCoordValue(index) - target.getCoordValue(index);
        index = (index + 1) % 2;
        System.out.println(" diff = root - target =" + diff);
        System.out.println(diff > 0 ? " search left " : " search right ");
        searchNearest(diff > 0 ? root.left : root.right, target, index);
        if(Math.sqrt(diff * diff) >= bestDistance){
            System.out.println(" target matched, Math.sqrt(diff * diff)=" + Math.sqrt(diff * diff) + " >= bestDistance=" + bestDistance);
            return;
        }
        System.out.println(" target unmatched, Math.sqrt(diff * diff)=" + Math.sqrt(diff * diff) + " < bestDistance=" + bestDistance);
        System.out.println(diff > 0 ? " search right " : " search left ");
        searchNearest(diff > 0 ? root.right : root.left, target, index);
    }

    public double getBestDistance() {
        return bestDistance;
    }

    private Node createTree(List<Node> nodes, int begin, int end, int index){
//        System.out.println(" createTree : nodes at (begin=" + begin + ", end=" + end + ") for index=" + index);
        if(end <= begin){
            return null;
        }
        int n = begin + (end - begin)/2;
        Node node = QuickSelect.select(nodes, begin, end - 1, n, new NodeComparator(index));
//        System.out.println(node.display(0));
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
        private double[] coords;
        private Node left = null;
        private Node right = null;

        public Node(double[] coords){
            this.coords = coords;
        }

        double[] getCoords(){
            return this.coords;
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
