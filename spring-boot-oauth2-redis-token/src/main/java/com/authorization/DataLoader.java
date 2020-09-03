package com.authorization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.authorization.common.UserInfoUtils;
import com.authorization.common.utils.UserInfo;
import com.authorization.user.model.entity.UserRoles;
import com.authorization.user.model.entity.Users;
import com.authorization.user.repository.UserRolesRepository;
import com.authorization.user.repository.UsersRepository;

@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	public UserRolesRepository userRolesRepository;


	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findAll().isEmpty()) {
			String roles[] = {"ROLE_ADMIN", "ROLE_BLOGGER"};
			Users users = new Users();
			users.setUsername("admin");
			users.setEnabled(true);
			users.setPassword(UserInfoUtils.getHashPassword("123"));
			users.setRoles(Arrays.asList(roles));
			List<UserRoles> userRolesList = new ArrayList<>();
			for (String string : roles) {
				UserRoles userRoles = new UserRoles();
				userRoles.setUsername(users.getUsername());
				userRoles.setRoleName(string);
				userRolesList.add(userRoles);
				
			}
			userRepository.save(users);
			userRolesRepository.save(userRolesList);
		}
	}
}
