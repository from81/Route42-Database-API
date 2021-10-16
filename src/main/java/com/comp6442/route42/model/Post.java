package com.comp6442.route42.model;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import com.google.firebase.internal.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
  private List<Point> route = new ArrayList<>();;
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
      List<Point> route,
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
    this.route = route;
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

  public List<Point> getRoute() {
    return route;
  }

  public void setRoute(List<Point> route) {
    this.route = route;
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

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Post{");
    sb.append("id='").append(id).append('\'');
    sb.append(", uid=").append(uid);
    sb.append(", userName='").append(userName).append('\'');
    sb.append(", isPublic=").append(isPublic);
    sb.append(", profilePicUrl='").append(profilePicUrl).append('\'');
    sb.append(", postDatetime=").append(postDatetime);
    sb.append(", postDescription='").append(postDescription).append('\'');
    sb.append(", locationName='").append(locationName).append('\'');
    sb.append(", latitude=").append(latitude);
    sb.append(", longitude=").append(longitude);
    sb.append(", route=").append(route);
    sb.append(", geohash='").append(geohash).append('\'');
    sb.append(", hashtags=").append(hashtags);
    sb.append(", likeCount=").append(likeCount);
    sb.append(", imageUrl='").append(imageUrl).append('\'');
    sb.append(", likedBy=").append(likedBy);
    sb.append('}');
    return sb.toString();
  }

  @NonNull
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Post post = (Post) obj;
    return Objects.equals(this.getId(), post.getId());
    // TODO
    //    return isPublic == post.isPublic && likeCount == post.likeCount && Objects.equals(uid,
    // post.uid) && Objects.equals(userName, post.userName) && Objects.equals(profilePicUrl,
    // post.profilePicUrl) && Objects.equals(postDatetime, post.postDatetime) &&
    // Objects.equals(postDescription, post.postDescription) && Objects.equals(locationName,
    // post.locationName) && Objects.equals(latitude, post.latitude) && Objects.equals(longitude,
    // post.longitude) && Objects.equals(geohash, post.geohash) && Objects.equals(hashtags,
    // post.hashtags) && Objects.equals(imageUrl, post.imageUrl) && Objects.equals(likedBy,
    // post.likedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uid, userName, isPublic, profilePicUrl, postDatetime, postDescription, locationName, latitude, longitude, route, geohash, hashtags, likeCount, imageUrl, likedBy);
  }
}
