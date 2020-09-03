package com.authorization.admin.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authorization.common.exceptions.RecordNotFoundException;
import com.authorization.common.messages.BaseResponse;
import com.authorization.common.messages.CustomMessage;
import com.authorization.common.utils.ApplicationUtils;
import com.authorization.user.model.dto.BlogDTO;
import com.authorization.user.model.dto.UsersDTO;
import com.authorization.user.model.entity.Blog;
import com.authorization.user.repository.BlogRepository;
import com.authorization.user.repository.CommentRepository;
import com.authorization.user.repository.LikeAndDislikeRepository;


@Service
@Transactional
public class AdminPostServiceImpl implements AdminPostService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private LikeAndDislikeRepository likeAndDislikeRepository;

	public List<BlogDTO> findPostByStatusAndPublish(int status, int publish) {
		List<Blog> deactivateUsersInfo = blogRepository.findByActiveStatusAndPublish(status, publish);
		return deactivateUsersInfo.stream().map(this::provideBlogToBlogDto).collect(Collectors.toList());
	}
	
	
	
	
	
	public BaseResponse approveAndPublishPendingPost(BlogDTO blogDTO) {
		blogRepository.approveAbdPublishPost(blogDTO.getBlogId(), blogDTO.getActiveStatus(), blogDTO.getPublish());
		return new BaseResponse(CustomMessage.BLOG_APPROVE_SUCCESS);
	}
	
	
	
	
	@Override
	public BaseResponse deleteBlogPostByBlogId(long blogId) {
		if (blogRepository.existsByBlogId(blogId)) {
			likeAndDislikeRepository.deleteByBlog_BlogId(blogId);
			commentRepository.deleteByBlog_BlogId(blogId);
			blogRepository.deleteByBlogId(blogId);
		} else {
			throw new RecordNotFoundException(CustomMessage.NO_RECOURD_FOUND + blogId);
		}
		return new BaseResponse(CustomMessage.BLOG_POST_DELETE);
	}

	
	
	
	public BlogDTO provideBlogToBlogDto(Blog blog) {
		BlogDTO blogDTO = new BlogDTO();
		UsersDTO usersDTO = new UsersDTO();
		BeanUtils.copyProperties(blog, blogDTO);
		blogDTO.setCreateDate(ApplicationUtils.convertDateToLocalDateTime(blog.getCreateDate()));
		BeanUtils.copyProperties(blog.getUsers(), usersDTO);
		blogDTO.setUsers(usersDTO);
		return blogDTO;
	}

	
	
	
	public Blog provideBlogDtoToBlog(BlogDTO blogDTO) {
		Blog blog = new Blog();
		BeanUtils.copyProperties(blogDTO, blog);
		return blog;
	}

}