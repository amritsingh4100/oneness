package com.amrit.oneness.Models;

public class CommentModel {
    String commentUserName;
    String commentContent;
    String commentTimeAgo;

    public CommentModel()
    {

    }

    public String getCommentThumbnailImageLink() {
        return commentThumbnailImageLink;
    }

    public void setCommentThumbnailImageLink(String commentThumbnailImageLink) {
        this.commentThumbnailImageLink = commentThumbnailImageLink;
    }

    String commentThumbnailImageLink;

    public CommentModel(String commentUserName, String commentContent, String commentTimeAgo, String commentThumbnailImageLink) {
        this.commentUserName = commentUserName;
        this.commentContent = commentContent;
        this.commentTimeAgo = commentTimeAgo;
        this.commentThumbnailImageLink= commentThumbnailImageLink;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentTimeAgo() {
        return commentTimeAgo;
    }

    public void setCommentTimeAgo(String commentTimeAgo) {
        this.commentTimeAgo = commentTimeAgo;
    }
}
