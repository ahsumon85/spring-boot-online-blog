package com.authorization.user.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.authorization.user.model.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

	List<Blog> findByActiveStatusAndPublish(int status, int publish);

//	void deleteByBlogIdAndUser_Id(long blogId, int userId);

	@Modifying
	@Query("UPDATE Blog b set b.activeStatus = ?2,  b.publish = ?3 where b.blogId = ?1")
	void approveAbdPublishPost(Long blogId, int activeStatus, int publish);

//	void deleteByBlogId(long blogId);

	boolean existsByBlogId(long blogId);

	void deleteByBlogIdAndUsers_Id(long blogId, int userId);

	void deleteByBlogId(long blogId);



}
