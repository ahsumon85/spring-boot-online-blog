/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.common.ApiExposeAndConsume;
import com.authentication.common.StaticValueProvider;
import com.authentication.common.StatusValue;
import com.authentication.loginUsers.LoginUserCredentialsProvider;
import com.authentication.user.model.BlogDTO;
import com.authentication.user.model.CommentDTO;
import com.authentication.user.model.LikeAndDislikeDTO;
import com.authentication.user.model.UsersDTO;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 *
 * @author Ahasan Habib
 */
@Service
public class AdminAndBloggerService {

	@Autowired
	private LoginUserCredentialsProvider token;

	public UsersDTO findUserInfosByUserName(String username) {
		UsersDTO usersDTO = ApiExposeAndConsume.consumeLoginUsersInfo(StaticValueProvider.LOGIN_USER_URI,
				"/info?username=" + username);
		return usersDTO;
	}

	public void signUpNewBlogger(UsersDTO userDTO) {
		String userRole[] = new String[] { "ROLE_BLOGGER" };
		userDTO.setEnabled(StatusValue.INACTIVE.getStatus());
		userDTO.setRoles(Arrays.asList(userRole));
		ApiExposeAndConsume.exposeLoginUsersDTO(StaticValueProvider.LOGIN_USER_URI, "/create/blogger", userDTO);

	}

	public void signUpNewAdmin(UsersDTO userDTO) {
		String userRole[] = new String[] { "ROLE_ADMIN", "ROLE_BLOGGER" };
		userDTO.setEnabled(StatusValue.ACTIVE.getStatus());
		userDTO.setRoles(Arrays.asList(userRole));
		ApiExposeAndConsume.exposeLoginUsersDTO(StaticValueProvider.LOGIN_USER_URI,"/create/admin?access_token=" + token.provideAccessToken(), userDTO);
	}

	public List<UsersDTO> findAllPendingUsers() {
		return ApiExposeAndConsume.consumeLoginUsersInfoList(StaticValueProvider.LOGIN_USER_URI,"/find/inactive/users?access_token=" + token.provideAccessToken());
	}
	
	
	public List<UsersDTO> findAllApprovedUsers() {
		return ApiExposeAndConsume.consumeLoginUsersInfoList(StaticValueProvider.LOGIN_USER_URI,"/find/active/users?access_token=" + token.provideAccessToken());
	}

	public void approvedFendingUser(Long userId) {

		try {
			ApiExposeAndConsume.exposeStringurlByPost(StaticValueProvider.BASE_URL + StaticValueProvider.LOGIN_USER_URI + "/approve/deactivate?userId=" + userId
					+ "&active=true&access_token=" + token.provideAccessToken());
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	public void deactivateApprovedUser(Long userId) {

		try {
			ApiExposeAndConsume.exposeStringurlByPost(StaticValueProvider.BASE_URL + StaticValueProvider.LOGIN_USER_URI + "/approve/deactivate?userId=" + userId
					+ "&active=false&access_token=" + token.provideAccessToken());
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	public void postBloggerContent(BlogDTO blogDTO) {
		blogDTO.setPublish(StatusValue.INACTIVE.getValue());
		blogDTO.setActiveStatus(StatusValue.INACTIVE.getValue());
		UsersDTO user = findUserInfosByUserName(LoginUserCredentialsProvider.provideUsername());
		blogDTO.setUsers(user);
		ApiExposeAndConsume.exposeBlogDTO(StaticValueProvider.LOGIN_BLOGGER_URI,"/post/create?access_token=" + token.provideAccessToken(), blogDTO);
	}

	public List<BlogDTO> findBloggerAllPost() {
	List<BlogDTO>  blogDTOs=  ApiExposeAndConsume.consumeBlogDTO(StaticValueProvider.LOGIN_BLOGGER_URI,"/find/post?access_token=" + token.provideAccessToken());
	blogDTOs.stream().map(blog ->{
		blog.setUserAndDate("Blogger : " + blog.getUsers().getUsername() +"   "+"Time : "+ blog.getCreateDate());
		return blog;
	}).collect(Collectors.toList());
	 return blogDTOs;
	}

	public List<BlogDTO> findAllBloggerPendingPost() {
		return ApiExposeAndConsume.consumeBlogDTO(StaticValueProvider.LOGIN_ADMIN_URI,"/pending/post?access_token=" + token.provideAccessToken());
	}
	
	public void approvedPostByAdmin(Long blogId) {
		BlogDTO blogDTO = new BlogDTO();
		blogDTO.setBlogId(blogId);
		blogDTO.setActiveStatus(StatusValue.ACTIVE.getValue());
		blogDTO.setPublish(StatusValue.ACTIVE.getValue());
		ApiExposeAndConsume.exposeBlogDTOWithPatch(StaticValueProvider.LOGIN_ADMIN_URI,"/approve?access_token=" + token.provideAccessToken(), blogDTO);
	}

	public void deleteOtherBloggerPost(Long blogId) {
		try {
			ApiExposeAndConsume.exposeStringurlByDelete(StaticValueProvider.BASE_URL + StaticValueProvider.LOGIN_ADMIN_URI + 
					"/delete?blogId="+ blogId + "&access_token=" + token.provideAccessToken());
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
	}

	public void likeBloggerPost(Long blogId) {
		LikeAndDislikeDTO likeAndDislikeDTO = new LikeAndDislikeDTO();
		BlogDTO blogDTO = new BlogDTO();
		likeAndDislikeDTO.setLikes(1);
		blogDTO.setBlogId(blogId);
		likeAndDislikeDTO.setBlog(blogDTO);
		ApiExposeAndConsume.exposeLikeDislikeDTO(StaticValueProvider.LOGIN_BLOGGER_URI, "/like/post?access_token=" + token.provideAccessToken(), likeAndDislikeDTO);
		
	}

	public void dislikeBloggerPost(Long blogId) {
		LikeAndDislikeDTO likeAndDislikeDTO = new LikeAndDislikeDTO();
		BlogDTO blogDTO = new BlogDTO();
		likeAndDislikeDTO.setDislikes(1);
		blogDTO.setBlogId(blogId);
		likeAndDislikeDTO.setBlog(blogDTO);
		ApiExposeAndConsume.exposeLikeDislikeDTO(StaticValueProvider.LOGIN_BLOGGER_URI, "/like/post?access_token=" + token.provideAccessToken(), likeAndDislikeDTO);
		
		
	}

	public void commentBloggerPost(CommentDTO commentDTO) {
		BlogDTO blogDTO = new BlogDTO();
		blogDTO.setBlogId(commentDTO.getBlogId());
		UsersDTO user = findUserInfosByUserName(LoginUserCredentialsProvider.provideUsername());
		commentDTO.setBlog(blogDTO);
		commentDTO.setUsers(user);
		commentDTO.setCommentId(null);
		ApiExposeAndConsume.exposeCommentDTO(StaticValueProvider.LOGIN_BLOGGER_URI, "/comment/post?access_token="+token.provideAccessToken(), commentDTO);
	}

	public List<BlogDTO> bloggerOwnPost() {
		UsersDTO user = findUserInfosByUserName(LoginUserCredentialsProvider.provideUsername());
		return findBloggerAllPost().stream().filter(blog -> blog.getUsers().getUserId() == user.getUserId()).collect(Collectors.toList());
	}







}
