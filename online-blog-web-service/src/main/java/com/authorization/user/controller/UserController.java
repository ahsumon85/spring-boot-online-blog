/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authorization.user.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.common.messages.BaseResponse;
import com.authorization.common.utils.StatusValue;
import com.authorization.user.model.dto.UserRolesDTO;
import com.authorization.user.model.dto.UsersDTO;
import com.authorization.user.service.UserService;

/**
 *
 * @author Ahasan Habib
 */

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@Autowired
	public UserService userService;

	@PostMapping(value = "/create/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<BaseResponse> createAminAccount(@RequestBody UsersDTO usersDTO) {
		BaseResponse baseResponse = userService.createAminOrBloggerAccount(usersDTO);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/create/blogger")
	public ResponseEntity<BaseResponse> createBloggerAccount(@RequestBody UsersDTO usersDTO) {
		BaseResponse baseResponse = userService.createAminOrBloggerAccount(usersDTO);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	@GetMapping(value = "/find/inactive/users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UsersDTO>> findAllInactiveBloggersInfoByStatus() {
		List<UsersDTO> list = userService.findAllUsersInfoByStatus(StatusValue.INACTIVE.getStatus());
		return new ResponseEntity<List<UsersDTO>>(list, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/find/active/users")
	public ResponseEntity<List<UsersDTO>> findAllActiveUsersInfoByStatus() {
		List<UsersDTO> list = userService.findAllUsersInfoByStatus(StatusValue.ACTIVE.getStatus());
		return new ResponseEntity<List<UsersDTO>>(list, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/approve/deactivate")
	public ResponseEntity<BaseResponse> approveAndDeactiveUsrByAdmin(
			@Valid @RequestParam @NotNull(message = "user Id not be null") Long userId,
			@Valid @RequestParam @NotNull(message = "active status not be null") boolean active) {
		BaseResponse response = userService.approveAndDeactiveUsrByAdmin(userId, active);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public List<UserRolesDTO> findLoginUserRoles(@RequestParam String username) {
		List<UserRolesDTO> roles = userService.findUserRolesByUsername(username);
		return roles;
	}

	@GetMapping(value = "/info")
	public UsersDTO findUserByUserName(@Valid @NotEmpty(message = "User name not be null") @RequestParam String username) {
		return userService.findUserByUserName(username);
	}

}
