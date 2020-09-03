package com.authorization.admin.service;

import java.util.List;

import com.authorization.common.messages.BaseResponse;
import com.authorization.user.model.dto.BlogDTO;


public interface AdminPostService {

	public List<BlogDTO> findPostByStatusAndPublish(int status, int publish);
	
	public BaseResponse approveAndPublishPendingPost(BlogDTO blogDTO);

	BaseResponse deleteBlogPostByBlogId(long blogId);
}
