package com.authorization.user.model.dto;

import com.authorization.user.model.entity.Blog;

public class LikeAndDislikeDTO {

	private Long likeDislikeId;

	private int likes;

	private int dislikes;

	private Blog blog;

	public Long getLikeDislikeId() {
		return likeDislikeId;
	}

	public void setLikeDislikeId(Long likeDislikeId) {
		this.likeDislikeId = likeDislikeId;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

}
