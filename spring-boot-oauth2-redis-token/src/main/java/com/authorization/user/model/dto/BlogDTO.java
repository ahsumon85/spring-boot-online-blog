package com.authorization.user.model.dto;

import java.util.List;

import com.authorization.user.model.entity.Comment;
import com.authorization.user.model.entity.LikeAndDislike;
import com.authorization.user.model.entity.Users;

public class BlogDTO {

	private Long blogId;

	private String content;

	private Integer activeStatus;

	private Integer publish;

	private String createDate;

	private LikeAndDislike bloggerLikeDislikes;

	private List<Comment> bloggerComment;

	private Users users;

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Integer getPublish() {
		return publish;
	}

	public void setPublish(Integer publish) {
		this.publish = publish;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public LikeAndDislike getBloggerLikeDislikes() {
		return bloggerLikeDislikes;
	}

	public void setBloggerLikeDislikes(LikeAndDislike bloggerLikeDislikes) {
		this.bloggerLikeDislikes = bloggerLikeDislikes;
	}

	public List<Comment> getBloggerComment() {
		return bloggerComment;
	}

	public void setBloggerComment(List<Comment> bloggerComment) {
		this.bloggerComment = bloggerComment;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}
