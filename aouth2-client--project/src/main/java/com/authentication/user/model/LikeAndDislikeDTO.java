package com.authentication.user.model;

public class LikeAndDislikeDTO {

	private Long likeDislikeId;

	private int likes;

	private int dislikes;

	private BlogDTO blog;

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

	public BlogDTO getBlog() {
		return blog;
	}

	public void setBlog(BlogDTO blog) {
		this.blog = blog;
	}

}