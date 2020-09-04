package com.authentication.user.model;

public class CommentDTO {

	private Long commentId;

	private String comment;

	private String commentDate;

	private BlogDTO blog;

	private UsersDTO users;
	
	private Long blogId;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public BlogDTO getBlog() {
		return blog;
	}

	public void setBlog(BlogDTO blog) {
		this.blog = blog;
	}

	public UsersDTO getUsers() {
		return users;
	}

	public void setUsers(UsersDTO users) {
		this.users = users;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

}
