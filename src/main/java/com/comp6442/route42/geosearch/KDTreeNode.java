package com.comp6442.route42.geosearch;

import com.comp6442.route42.model.Post;
import com.google.cloud.firestore.GeoPoint;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

public class KDTreeNode extends GeoPoint {
  private KDTreeNode left = null;
  private KDTreeNode right = null;
  private final Double[] coords = new Double[2];
  private Post post;

  public KDTreeNode(double latitude, double longitude) {
    super(latitude, longitude);
    this.coords[0] = longitude;
    this.coords[1] = latitude;
  }

  public KDTreeNode(Post post) {
    this(post.getLatitude(), post.getLongitude());
    this.post = post;
  }

  public static KDTreeNode fromNodes(List<KDTreeNode> nodes, int begin, int end, int index) {
    if (end <= begin) {
      return null;
    }
    int n = begin + (end - begin) / 2;
    KDTreeNode node = QuickSelect.select(nodes, begin, end - 1, n, new NodeComparator(index));
    index = (index + 1) % 2;
    node.setLeft(fromNodes(nodes, begin, n, index));
    node.setRight(fromNodes(nodes, n + 1, end, index));
    return node;
  }

  public static KDTreeNode fromPost(Post post) {
    return new KDTreeNode(post);
  }

  public Double getCoordValue(int idx) {
    return this.coords[idx];
  }

  public KDTreeNode getLeft() {
    return left;
  }

  public void setLeft(KDTreeNode left) {
    this.left = left;
  }

  public KDTreeNode getRight() {
    return right;
  }

  public void setRight(KDTreeNode right) {
    this.right = right;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  @Nonnull
  public String toString() {
    return super.toString();
  }

  public String display(int tabs) {
    StringBuilder SB = new StringBuilder();
    SB.append(super.toString());
    if (this.left != null) {
      SB.append("\n").append("\t".repeat(tabs)).append("├─").append(this.left.display(tabs + 1));
    }
    if (this.right != null) {
      SB.append("\n").append("\t".repeat(tabs)).append("├─").append(this.right.display(tabs + 1));
    }
    return SB.toString();
  }

  public double getDistanceTo(KDTreeNode other) {
    double earthRadius = 3958.75;
    int meterConversion = 1609;

    double dLat = Math.toRadians(other.getLatitude() - this.getLatitude());
    double dLng = Math.toRadians(other.getLongitude() - this.getLongitude());
    double a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2)
            + Math.cos(Math.toRadians(this.getLatitude()))
                * Math.cos(Math.toRadians(other.getLatitude()))
                * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double dist = earthRadius * c;
    return dist * meterConversion;
  }

  public static class NodeComparator implements Comparator<KDTreeNode> {
    private final int index;

    public NodeComparator(int index) {
      this.index = index;
    }

    @Override
    public int compare(KDTreeNode node1, KDTreeNode node2) {
      return Double.compare(node1.getCoordValue(index), node2.getCoordValue(index));
    }
  }
}
