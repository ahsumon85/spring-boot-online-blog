package com.authentication.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.authentication.loginUsers.LoginUserCredentialsProvider;
import com.authentication.service.AdminAndBloggerService;
import com.authentication.user.model.BlogDTO;
import com.authentication.user.model.CommentDTO;
import com.authentication.user.model.UsersDTO;

import javassist.expr.NewArray;

@Controller
public class AdminAndBlogerController {

	@Autowired
	private AdminAndBloggerService adminAndBloggerService;
	
	@GetMapping(value = "/admin/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		UsersDTO user = adminAndBloggerService.findUserInfosByUserName(LoginUserCredentialsProvider.provideUsername());
		modelAndView.addObject("userName", "Welcome " + user.getUsername());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

	@GetMapping(value = "/registration")
	public ModelAndView bloggerSignUpFrom() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("bloggerDTO", new UsersDTO());
		modelAndView.setViewName("blogger-registration");
		return modelAndView;
	}

	@PostMapping(value = "/registration")
	public ModelAndView signUpNewBlogger(@Valid @ModelAttribute("bloggerDTO") UsersDTO bloggerDTO) {
		ModelAndView modelAndView = new ModelAndView();
		UsersDTO user = adminAndBloggerService.findUserInfosByUserName(bloggerDTO.getUsername());
		if (user == null) {
			adminAndBloggerService.signUpNewBlogger(bloggerDTO);
			modelAndView.addObject("user", new UsersDTO());
			modelAndView.setViewName("login");
		} else {
			modelAndView.addObject("message", "Sorry ! you are already registered user");
			modelAndView.setViewName("blogger-registration");
		}
		return modelAndView;
	}

	@GetMapping(value = "/create/admin")
	public ModelAndView adminSignUpFrom() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("adminDTO", new UsersDTO());
		modelAndView.setViewName("admin-registration");
		return modelAndView;
	}

	@PostMapping(value = "/create/admin")
	public ModelAndView signUpNewAdmin(@Valid @ModelAttribute("adminDTO") UsersDTO adminDTO) {
		ModelAndView modelAndView = new ModelAndView();
		UsersDTO user = adminAndBloggerService.findUserInfosByUserName(adminDTO.getUsername());
		if (user == null) {
			adminAndBloggerService.signUpNewAdmin(adminDTO);
		} else {
			modelAndView.addObject("message", "Sorry ! you are already registered user");
		}
		modelAndView.setViewName("admin-registration");
		return modelAndView;
	}

	@GetMapping(value = "/pending/users")
	public ModelAndView findAllPendingUsers() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userInfos", adminAndBloggerService.findAllPendingUsers());
		modelAndView.setViewName("pending-blogger");
		return modelAndView;
	}

	@GetMapping(value = "/approved/user")
	public ModelAndView approvedFendingUser(@NotNull @RequestParam Long userId) {
		adminAndBloggerService.approvedFendingUser(userId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userInfos", adminAndBloggerService.findAllPendingUsers());
		modelAndView.setViewName("pending-blogger");
		return modelAndView;
	}
	
	@GetMapping(value = "/active/users")
	public ModelAndView findAllApprovedUsers() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("approvedUsers", adminAndBloggerService.findAllApprovedUsers());
		modelAndView.setViewName("approved-blogger");
		return modelAndView;
	}
	
	@GetMapping(value = "/deactivate/users")
	public ModelAndView deactivateApprovedUser(@NotNull @RequestParam Long userId) {
		adminAndBloggerService.deactivateApprovedUser(userId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("approvedUsers", adminAndBloggerService.findAllApprovedUsers());
		modelAndView.setViewName("approved-blogger");
		return modelAndView;
	}
	
	@GetMapping(value = "/blogger/own/post")
	public ModelAndView bloggerOwnPost() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("commentOwnDTO", new CommentDTO());
		modelAndView.addObject("blogOwnDTOs", adminAndBloggerService.bloggerOwnPost());
		modelAndView.setViewName("blogger-own-post");
		return modelAndView;
	}
	
	@GetMapping(value = "/blogger/post")
	public ModelAndView findBloggerAllPost() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("blogPostDTO", new BlogDTO());
		modelAndView.addObject("commentDTO", new CommentDTO());
		modelAndView.addObject("blogDTOs", adminAndBloggerService.findBloggerAllPost());
		modelAndView.setViewName("blogger-post");
		return modelAndView;
	}
	
	
	@PostMapping(value = "/blog/post")
	public String postBloggerContent(@Valid @ModelAttribute("blogPostDTO") BlogDTO blogPostDTO) {
		adminAndBloggerService.postBloggerContent(blogPostDTO);
		return "redirect:/blogger/post";
	}
	
	@PostMapping(value = "/comment/post")
	public String commentBloggerPost(@Valid @ModelAttribute("commentDTO") CommentDTO commentDTO) {
		adminAndBloggerService.commentBloggerPost(commentDTO);
		return "redirect:/blogger/post";
	}
	
	@PostMapping(value = "/comment/own/post")
	public String commentBloggerOwnPost(@Valid @ModelAttribute("commentOwnDTO") CommentDTO commentOwnDTO) {
		adminAndBloggerService.commentBloggerPost(commentOwnDTO);
		return "redirect:/blogger/own/post";
	}
	
	

	@GetMapping(value = "/pending/post")
	public ModelAndView findAllBloggerPendingPost() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pendingBlogInfos", adminAndBloggerService.findAllBloggerPendingPost());
		modelAndView.setViewName("pending-post");
		return modelAndView;
	}
	
	
	@GetMapping(value = "/approved/post")
	public String approvedPostByAdmin(@Valid @RequestParam Long blogId) {
		adminAndBloggerService.approvedPostByAdmin(blogId);
		return "redirect:/pending/post";
	}
	
	@GetMapping(value = "/delete/blogger/post")
	public String deleteOtherBloggerPost(@NotNull @RequestParam Long blogId) {
		adminAndBloggerService.deleteOtherBloggerPost(blogId);
		return "redirect:/blogger/post";
	}
	
	@GetMapping(value = "/delete/own/post")
	public String deleteOwnBloggerPost(@NotNull @RequestParam Long blogId) {
		adminAndBloggerService.deleteOtherBloggerPost(blogId);
		return "redirect:/blogger/own/post";
	}
	
	@GetMapping(value = "/like/blogger/post")
	public String likeBloggerPost(@NotNull @RequestParam Long blogId) {
		adminAndBloggerService.likeBloggerPost(blogId);
		return "redirect:/blogger/post";
	}
	
	@GetMapping(value = "/dislike/blogger/post")
	public String dislikeBloggerPost(@NotNull @RequestParam Long blogId) {
		adminAndBloggerService.dislikeBloggerPost(blogId);
		return "redirect:/blogger/post";
	}

	
}
