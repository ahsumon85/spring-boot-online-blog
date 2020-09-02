package com.authorization.blogger.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authorization.common.exceptions.RecordNotFoundException;
import com.authorization.common.messages.BaseResponse;
import com.authorization.common.messages.CustomMessage;
import com.authorization.common.utils.ApplicationUtils;
import com.authorization.common.utils.StatusValue;
import com.authorization.user.model.dto.BlogDTO;
import com.authorization.user.model.dto.CommentDTO;
import com.authorization.user.model.dto.LikeAndDislikeDTO;
import com.authorization.user.model.entity.Blog;
import com.authorization.user.model.entity.Comment;
import com.authorization.user.model.entity.LikeAndDislike;
import com.authorization.user.model.entity.Users;
import com.authorization.user.repository.BlogRepository;
import com.authorization.user.repository.CommentRepository;
import com.authorization.user.repository.LikeAndDislikeRepository;



@Service
@Transactional
public class BloggerServiceImpl implements BloggerService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private LikeAndDislikeRepository likeAndDislikeRepository;

	@Override
	public BaseResponse createBlogPostByBlogger(@Valid BlogDTO blogDTO) {
		Blog blog = copyBlogDtoToBlog(blogDTO);
		blogRepository.save(blog);
		return new BaseResponse(CustomMessage.BLOG_POST_SUCCESS);
	}

	
	
	@Override
	public BaseResponse deleteOwnBlogPostById(int userId, long blogId) {
		if (blogRepository.existsByBlogId((int) blogId)) {
			likeAndDislikeRepository.deleteByBlog_BlogId(blogId);
			commentRepository.deleteByBlog_BlogId(blogId);
			blogRepository.deleteByBlogIdAndUsers_Id(blogId, userId);
		} else {
			throw new RecordNotFoundException(CustomMessage.NO_RECOURD_FOUND + blogId);
		}
		return new BaseResponse(CustomMessage.BLOG_POST_DELETE);
	}

	
	public List<BlogDTO> findAllApproedBloggerPost(int status, int publish) {
		List<Blog> deactivateUsersInfo = blogRepository.findByActiveStatusAndPublish(status, publish);
		List<LikeAndDislike> postLikeDislikeList = likeAndDislikeRepository.findAll();
		List<Comment> psotCommentList = commentRepository.findAll();
		return deactivateUsersInfo.stream().map(blog -> provideBlogToBlogDto(blog, psotCommentList, postLikeDislikeList)).collect(Collectors.toList());
	}	
	
	
	
	public BaseResponse likeAndDislikeOtherApprvPost(LikeAndDislikeDTO likeAndDislikeDTO) {
		LikeAndDislike likeAndDislike = copyLikeAndDislikeDtoToEntity(likeAndDislikeDTO);
		likeAndDislikeRepository.save(likeAndDislike);
		return new BaseResponse(CustomMessage.LIKE_SUCCESS);
	}
	
	
	private LikeAndDislike copyLikeAndDislikeDtoToEntity(LikeAndDislikeDTO likeAndDislikeDTO) {
		LikeAndDislike likeAndDislike = new LikeAndDislike();
		LikeAndDislike recourdedLikeAndDis = likeAndDislikeRepository.findByBlog_blogId(likeAndDislikeDTO.getBlog().getBlogId());
		BeanUtils.copyProperties(likeAndDislikeDTO, likeAndDislike);
		likeAndDislike.setBlog(provideBlogByBlogId(likeAndDislikeDTO.getBlog().getBlogId()));
		if(recourdedLikeAndDis.getLikes() == StatusValue.LIKE.getValue()) {
			likeAndDislike.setLikes(likeAndDislikeDTO.getLikes() + 1);
		}else {
			likeAndDislike.setLikes(recourdedLikeAndDis.getLikes());
		}
		if (recourdedLikeAndDis.getDislikes() == StatusValue.DISLIKE.getValue()) {
			likeAndDislike.setDislikes(likeAndDislikeDTO.getDislikes() + 1);
		}else {
			likeAndDislike.setDislikes(recourdedLikeAndDis.getDislikes());
		}
		return likeAndDislike;
	}

	
	
	
	
	
	public BaseResponse commentOtherApprovedPost(CommentDTO commentDTO) {
		Comment comment = copyCommentDtoToEntity(commentDTO);
		commentRepository.save(comment);
		return new BaseResponse(CustomMessage.COMMENT_SUCCESS);
	}
	
	
	
	
	private Comment copyCommentDtoToEntity(CommentDTO commentDTO) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentDTO, comment);
		comment.setBlog(provideBlogByBlogId(commentDTO.getBlog().getBlogId()));
		comment.setUsers(provideUserByUserId(commentDTO.getUsers().getId()));
		return comment;
	}

	
	
	
	private Blog copyBlogDtoToBlog(@Valid BlogDTO blogDTO) {
		Blog blog = new Blog();
		BeanUtils.copyProperties(blogDTO, blog);
		blog.setUsers(provideUserByUserId(blogDTO.getUsers().getId()));
		return blog;
	}
	
	public Users provideUserByUserId(long userId) {
		Users user = new Users();
		user.setId(userId);
		return user;
	}
	
	
	
	public Blog provideBlogByBlogId(Long blogId) {
		Blog blog = new Blog();
		blog.setBlogId(blogId);
		return blog;
	}

	
	
	public Blog provideBlogDtoToBlog(BlogDTO blogDTO) {
		Blog blog = new Blog();
		BeanUtils.copyProperties(blogDTO, blog);
		return blog;
	}

	
	
	public BlogDTO provideBlogToBlogDto(Blog blog, List<Comment> psotCommentList, List<LikeAndDislike> postLikeDislikeList) {
		BlogDTO blogDTO = new BlogDTO();
		List<Comment> commentListByBlogId = psotCommentList.stream().filter(com -> com.getBlog().getBlogId() == blog.getBlogId()).collect(Collectors.toList());
		LikeAndDislike likeDislikeByBlogId = postLikeDislikeList.stream().filter(likeDislike -> likeDislike.getBlog().getBlogId() == blog.getBlogId()).findFirst().orElse(null);
		BeanUtils.copyProperties(blog, blogDTO);
		blogDTO.setCreateDate(ApplicationUtils.convertDateToLocalDateTime(blog.getCreateDate()));
		blogDTO.setBloggerLikeDislikes(likeDislikeByBlogId);
		blogDTO.setBloggerComment(commentListByBlogId);
		return blogDTO;
	}

}