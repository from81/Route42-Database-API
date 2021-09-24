package com.comp6442.route42.model;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import com.google.firebase.internal.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@IgnoreExtraProperties
public class Post extends Model implements Serializable {
  private DocumentReference uid;
  private String userName;
  private int isPublic;
  private String profilePicUrl;
  private Date postDatetime;
  private String postDescription = "";
  private String locationName;
  private Double latitude;
  private Double longitude;
  private String geohash = "";
  private List<String> hashtags = new ArrayList<>();
  private int likeCount = 0;
  private String imageUrl;
  private List<DocumentReference> likedBy = new ArrayList<>();

  public Post() {}

  public Post(
      String id,
      DocumentReference uid,
      String userName,
      int isPublic,
      String profilePicUrl,
      Date postDatetime,
      String postDescription,
      String locationName,
      Double latitude,
      Double longitude,
      List<String> hashtags,
      int likeCount,
      String imageUrl,
      List<DocumentReference> likedBy,
      String geohash) {

    this.id = id;
    this.uid = uid;
    this.userName = userName;
    this.isPublic = isPublic;
    this.profilePicUrl = profilePicUrl;
    this.postDatetime = postDatetime;
    this.postDescription = postDescription;
    this.locationName = locationName;
    this.latitude = latitude;
    this.longitude = longitude;
    this.hashtags = hashtags;
    this.likeCount = likeCount;
    this.imageUrl = imageUrl;
    this.likedBy = likedBy;
    this.geohash = geohash;
  }

  public String getUid() {
    return uid.getId().replaceAll("^\"|\"$", "");
  }

  public void setUid(DocumentReference uid) {
    this.uid = uid;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(int isPublic) {
    this.isPublic = isPublic;
  }

  public String getProfilePicUrl() {
    return profilePicUrl;
  }

  public void setProfilePicUrl(String profilePicUrl) {
    this.profilePicUrl = profilePicUrl;
  }

  public Date getPostDatetime() {
    return postDatetime;
  }

  public void setPostDatetime(Date postDatetime) {
    this.postDatetime = postDatetime;
  }

  public String getPostDescription() {
    return postDescription;
  }

  public void setPostDescription(String postDescription) {
    this.postDescription = postDescription;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public List<String> getHashtags() {
    return hashtags;
  }

  public void setHashtags(List<String> hashtags) {
    this.hashtags = hashtags;
  }

  public int getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(int likeCount) {
    this.likeCount = likeCount;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public List<String> getLikedBy() {
    return likedBy.stream()
        .map(DocumentReference::getId)
        .map(str -> str.replaceAll("^\"|\"$", ""))
        .collect(Collectors.toList());
  }

  public void setLikedBy(List<DocumentReference> likedBy) {
    this.likedBy = likedBy;
  }

  public String getGeohash() {
    return geohash;
  }

  public void setGeohash(String geohash) {
    this.geohash = geohash;
  }

  @NonNull
  @Override
  public String toString() {
    String sb = "Post{" + "id='" + id + '\'' +
            ", uid=" + uid +
            ", userName='" + userName + '\'' +
            ", isPublic=" + isPublic +
            ", profilePicUrl='" + profilePicUrl + '\'' +
            ", postDatetime=" + postDatetime +
            ", postDescription='" + postDescription + '\'' +
            ", locationName='" + locationName + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", geohash='" + geohash + '\'' +
            ", hashtags=" + hashtags +
            ", likeCount=" + likeCount +
            ", imageUrl='" + imageUrl + '\'' +
            ", likedBy=" + likedBy +
            '}';
    return sb;
  }
}
