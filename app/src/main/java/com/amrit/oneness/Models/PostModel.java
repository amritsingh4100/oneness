package com.amrit.oneness.Models;

import java.util.ArrayList;
import java.util.List;

public class PostModel {

    String postTitle;
    String postDetails;
    String postImageLink;
    String postUserName;
    String userID;

    public String getPostUserThumbnailImageLink() {
        return postUserThumbnailImageLink;
    }

    public void setPostUserThumbnailImageLink(String postUserThumbnailImageLink) {
        this.postUserThumbnailImageLink = postUserThumbnailImageLink;
    }

    String postUserThumbnailImageLink;

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    String postKey;

    public long getPostTimeAgo() {
        return postTimeAgo;
    }

    long postTimeAgo;
    ArrayList<CommentModel> commentModelArrayList = new ArrayList<>();
    List<InterestModel> interestArrayList= new ArrayList<>();

    public PostModel()
    {

    }

    public PostModel(String postTitle, String postDetails,String postUserThumbnailImageLink, String postImageLink, String postUserName,String userID, long postTimeAgo,
                     ArrayList<CommentModel> commentModelArrayList, List<InterestModel> interestArrayList) {
        this.postTitle = postTitle;
        this.postDetails = postDetails;
        this.postImageLink = postImageLink;
        this.postUserName = postUserName;
        this.postTimeAgo = postTimeAgo;
        this.commentModelArrayList = commentModelArrayList;
        this.interestArrayList=interestArrayList;
        this.postUserThumbnailImageLink = postUserThumbnailImageLink;
        this.userID=userID;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(String postDetails) {
        this.postDetails = postDetails;
    }

    public String getPostImageLink() {
        return postImageLink;
    }

    public void setPostImageLink(String postImageLink) {
        this.postImageLink = postImageLink;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public ArrayList<CommentModel> getCommentModelArrayList() {
        return commentModelArrayList;
    }

    public void setCommentModelArrayList(ArrayList<CommentModel> commentModelArrayList) {
        this.commentModelArrayList = commentModelArrayList;
    }
    public void setPostTimeAgo(long postTimeAgo) {
        this.postTimeAgo = postTimeAgo;
    }

    public void setInterestArrayList(List<InterestModel> interestArrayList) {
        this.interestArrayList = interestArrayList;
    }

    public List<InterestModel> getInterestArrayList() {
        return interestArrayList;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
