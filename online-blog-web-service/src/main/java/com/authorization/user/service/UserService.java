package com.authorization.user.service;

import java.util.List;

import javax.validation.Valid;

import com.authorization.common.messages.BaseResponse;
import com.authorization.user.model.dto.UserRolesDTO;
import com.authorization.user.model.dto.UsersDTO;

public interface UserService {

	public BaseResponse createAminOrBloggerAccount(@Valid UsersDTO usersDTO);

	public List<UsersDTO> findAllUsersInfoByStatus(boolean status);

	public BaseResponse approveAndDeactiveUsrByAdmin(Long userId, boolean active);

	public List<UserRolesDTO> findUserRolesByUsername(String username);

	public UsersDTO findUserByUserName(String username);
	

}
