package com.authorization.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authorization.common.UserInfoUtils;
import com.authorization.common.messages.BaseResponse;
import com.authorization.common.messages.CustomMessage;
import com.authorization.common.utils.StatusValue;
import com.authorization.common.utils.UserInfo;
import com.authorization.user.model.dto.UserRolesDTO;
import com.authorization.user.model.dto.UsersDTO;
import com.authorization.user.model.entity.UserRoles;
import com.authorization.user.model.entity.Users;
import com.authorization.user.repository.UserRolesRepository;
import com.authorization.user.repository.UsersRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	public UserRolesRepository userRolesRepository;

	@Transactional
	public BaseResponse createAminOrBloggerAccount(UsersDTO usersDTO) {
		Users user = provideUserDtoToUser(usersDTO);
		List<UserRoles> userRoles = provideUsrRolesByUsrDto(usersDTO);
		userRepository.save(user);
		userRolesRepository.save(userRoles);
		return new BaseResponse(CustomMessage.SAVE_SUCCESS_MESSAGE);
	}

	public List<UsersDTO> findAllUsersInfoByStatus(boolean status) {
		List<Users> deactivateUsersInfo = userRepository.findByEnabled(status);
		return deactivateUsersInfo.stream().map(this::provideUserToUserDto).collect(Collectors.toList());
	}

	@Transactional
	public BaseResponse approveAndDeactiveUsrByAdmin(Long userId, boolean status) {
		userRepository.approveIncativeUser(userId, status);
		return new BaseResponse(UserInfo.BLOGGER.getType() + CustomMessage.BLOGGER_ACTIVE_SUCCESS);
	}

	@Transactional
	public List<UserRolesDTO> findUserRolesByUsername(String username) {
		List<UserRoles> roles = new ArrayList<>();
		roles = userRolesRepository.findByUsername(username);
		if (roles != null) {
			return roles.stream().map(role -> copyUserRoleToUserRoleDTO(role)).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public UsersDTO findUserByUserName(String username) {
		Users users = userRepository.findByUsername(username);
		return users != null ? provideUserToUserDto(users) : new UsersDTO();
	}

	private List<UserRoles> provideUsrRolesByUsrDto(UsersDTO userDTO) {
		List<UserRoles> userRolesList = new ArrayList<>();
		userDTO.getRoles().forEach(role -> {
			UserRoles userRoles = new UserRoles();
			userRoles.setUsername(userDTO.getUsername());
			userRoles.setRoleName(role);
			userRolesList.add(userRoles);
		});
		return userRolesList;
	}

	public Users provideUserDtoToUser(UsersDTO usersDTO) {
		Users users = new Users();
		BeanUtils.copyProperties(usersDTO, users);
		users.setPassword(UserInfoUtils.getHashPassword(usersDTO.getPassword()));
		return users;
	}

	public UsersDTO provideUserToUserDto(Users user) {
		UsersDTO userDTO = new UsersDTO();
		BeanUtils.copyProperties(user, userDTO);
		userDTO.setUserId(user.getId());
		return userDTO;
	}

	public UserRolesDTO copyUserRoleToUserRoleDTO(UserRoles userRole) {
		UserRolesDTO usersRoleDTO = new UserRolesDTO();
		BeanUtils.copyProperties(userRole, usersRoleDTO);
		return usersRoleDTO;
	}

}