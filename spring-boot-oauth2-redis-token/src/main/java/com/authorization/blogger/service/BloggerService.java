package com.authorization.blogger.service;

import java.util.List;

import javax.validation.Valid;

import com.authorization.common.messages.BaseResponse;
import com.authorization.user.model.dto.BlogDTO;

public interface BloggerService {

	public BaseResponse createBlogPostByBlogger(@Valid BlogDTO blogDTO);

	public BaseResponse deleteOwnBlogPostById(int userId, long blogId);
	
	public List<BlogDTO> findAllApproedBloggerPost(int status, int publish);
}
