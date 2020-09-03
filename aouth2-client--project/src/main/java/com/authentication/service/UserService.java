/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.common.ApiConsume;
import com.authentication.common.StaticValueProvider;
import com.authentication.common.StatusValue;
import com.authentication.loginUsers.AccessTokenProviderService;
import com.authentication.user.model.UsersDTO;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 *
 * @author Ahasan Habib
 */
@Service
public class UserService {

	@Autowired
	private AccessTokenProviderService token;

	public UsersDTO findUserInfosByUserName(String username) {
		UsersDTO usersDTO = ApiConsume.consumeLoginUsersInfo(StaticValueProvider.LOGIN_USER_URI,
				"/info?username=" + username);
		return usersDTO;
	}

	public void signUpNewBlogger(UsersDTO userDTO) {
		String userRole[] = new String[] { "ROLE_BLOGGER" };
		userDTO.setEnabled(StatusValue.INACTIVE.getStatus());
		userDTO.setRoles(Arrays.asList(userRole));
		ApiConsume.exposeLoginUsersDTO(StaticValueProvider.LOGIN_USER_URI, "/create/blogger", userDTO);

	}

	public void signUpNewAdmin(UsersDTO userDTO) {
		String userRole[] = new String[] { "ROLE_ADMIN", "ROLE_BLOGGER" };
		userDTO.setEnabled(StatusValue.ACTIVE.getStatus());
		userDTO.setRoles(Arrays.asList(userRole));
		ApiConsume.exposeLoginUsersDTO(StaticValueProvider.LOGIN_USER_URI,"/create/admin?access_token=" + token.provideAccessToken(), userDTO);
	}

	public List<UsersDTO> findAllPendingUsers() {
		return ApiConsume.consumeLoginUsersInfoList(StaticValueProvider.LOGIN_USER_URI,"/find/inactive/users?access_token=" + token.provideAccessToken());
	}
	
	
	public List<UsersDTO> findAllApprovedUsers() {
		return ApiConsume.consumeLoginUsersInfoList(StaticValueProvider.LOGIN_USER_URI,"/find/active/users?access_token=" + token.provideAccessToken());
	}

	public void approvedFendingUser(Long userId) {

		try {
			ApiConsume.exposeStringurl(StaticValueProvider.BASE_URL + StaticValueProvider.LOGIN_USER_URI + "/approve/deactivate?userId=" + userId
					+ "&active=true&access_token=" + token.provideAccessToken());
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	public void deactivateApprovedUser(Long userId) {

		try {
			ApiConsume.exposeStringurl(StaticValueProvider.BASE_URL + StaticValueProvider.LOGIN_USER_URI + "/approve/deactivate?userId=" + userId
					+ "&active=false&access_token=" + token.provideAccessToken());
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
