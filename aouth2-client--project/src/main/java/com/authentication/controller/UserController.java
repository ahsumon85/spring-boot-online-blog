package com.authentication.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.authentication.loginUsers.AccessTokenProviderService;
import com.authentication.loginUsers.LoginService;
import com.authentication.service.UserService;
import com.authentication.user.model.UsersDTO;
import com.mashape.unirest.http.exceptions.UnirestException;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

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
		UsersDTO user = userService.findUserInfosByUserName(bloggerDTO.getUsername());
		if (user == null) {
			userService.signUpNewBlogger(bloggerDTO);
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
		UsersDTO user = userService.findUserInfosByUserName(adminDTO.getUsername());
		if (user == null) {
			userService.signUpNewAdmin(adminDTO);
		} else {
			modelAndView.addObject("message", "Sorry ! you are already registered user");
		}
		modelAndView.setViewName("admin-registration");
		return modelAndView;
	}

	@GetMapping(value = "/pending/users")
	public ModelAndView findAllPendingUsers() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userInfos", userService.findAllPendingUsers());
		modelAndView.setViewName("pending-blogger");
		return modelAndView;
	}

	@GetMapping(value = "/approved/user")
	public ModelAndView approvedFendingUser(@NotNull @RequestParam Long userId) {
		userService.approvedFendingUser(userId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userInfos", userService.findAllPendingUsers());
		modelAndView.setViewName("pending-blogger");
		return modelAndView;
	}
	
	@GetMapping(value = "/active/users")
	public ModelAndView findAllApprovedUsers() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("approvedUsers", userService.findAllApprovedUsers());
		modelAndView.setViewName("approved-blogger");
		return modelAndView;
	}
	
	@GetMapping(value = "/deactivate/users")
	public ModelAndView deactivateApprovedUser(@NotNull @RequestParam Long userId) {
		userService.deactivateApprovedUser(userId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("approvedUsers", userService.findAllApprovedUsers());
		modelAndView.setViewName("approved-blogger");
		return modelAndView;
	}

	/*
	 * @RequestMapping(value = "/new/password", method = RequestMethod.POST) public
	 * ModelAndView newPassword(UserDTO userDTO) { ModelAndView modelAndView = new
	 * ModelAndView(); Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication();
	 * 
	 * if (userDTO.getNewPass().equals(userDTO.getConfirmPass())) { User user =
	 * userService.findUserByEmail(auth.getName()); Boolean status =
	 * userService.isPasswordValid(userDTO.getPassword(), user.getPassword()); if
	 * (status == true) {
	 * 
	 * userService.changePasswor(user, userDTO); modelAndView.setViewName("login");
	 * } else { modelAndView.addObject("wrongPass",
	 * "Current possword was wrong..!");
	 * modelAndView.setViewName("password-update"); }
	 * 
	 * } else { modelAndView.addObject("passMatched",
	 * "Password doesn't matched..!"); modelAndView.setViewName("password-update");
	 * } return modelAndView; }
	 */

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		UsersDTO user = userService.findUserInfosByUserName(AccessTokenProviderService.provideUsername());
		modelAndView.addObject("userName", "Welcome " + user.getUsername());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

}
