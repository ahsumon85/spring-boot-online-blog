package com.authorization.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.authorization.user.model.entity.Comment;



public interface CommentRepository extends JpaRepository<Comment, Integer> {

	void deleteByBlog_BlogId(long blogId);

}
