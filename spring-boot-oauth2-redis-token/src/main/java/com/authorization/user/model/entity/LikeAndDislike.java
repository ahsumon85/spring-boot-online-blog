package com.authorization.user.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "like_dislike")
public class LikeAndDislike {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "like_dis_id")
	private Long likeDislikeId;

	@Column(name = "likes")
	private int likes;

	@Column(name = "dislikes")
	private int dislikes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blog_id")
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
