package com.authorization.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authorization.user.model.entity.LikeAndDislike;

public interface LikeAndDislikeRepository extends JpaRepository<LikeAndDislike, Integer> {

	void deleteByBlog_BlogId(long blogId);

	LikeAndDislike findByBlog_BlogId(Long blogId);

}
