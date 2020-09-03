package com.authorization.user.model.dto;

import com.authorization.user.model.entity.Blog;
import com.authorization.user.model.entity.Users;

public class CommentDTO {

	private Long commentId;

	private String comment;

	private String commentDate;

	private Blog blog;

	private UsersDTO users;

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

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public UsersDTO getUsers() {
		return users;
	}

	public void setUsers(UsersDTO users) {
		this.users = users;
	}

}
