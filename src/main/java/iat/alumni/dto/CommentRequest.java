package iat.alumni.dto;

import jakarta.validation.constraints.Size;

public class CommentRequest {
	@Size(max = 1000, message = "Comment must be less than 1000 characters")
    private String commentText;
	
	public CommentRequest() {
    }

    public CommentRequest(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
