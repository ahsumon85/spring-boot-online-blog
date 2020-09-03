package com.authentication.loginUsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.authentication.common.ApiConsume;
import com.authentication.common.StaticValueProvider;
import com.authentication.user.model.UserRolesDTO;

@Controller
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private LoginService loginService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();

		String password = authentication.getCredentials().toString();

		String accessToken = loginService.porvideAccessToken(username, password);

		if (accessToken != null) {
			List<UserRolesDTO> userRoleList = findLoginUserRoles(username, accessToken);
			if (userRoleList == null) {
				userRoleList = new ArrayList<>();
				UserRolesDTO userRoleDTO = new UserRolesDTO();
				userRoleDTO.setUsername(username);
				userRoleList.add(userRoleDTO);
			}
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			for (UserRolesDTO ur : userRoleList) {
				grantedAuthorities.add(new SimpleGrantedAuthority(ur.getRoleName()));
			}

			return new UsernamePasswordAuthenticationToken(username + "," + accessToken, password, grantedAuthorities);

		} else {
			throw new UsernameNotFoundException("Sorry! Username or Password is invalid");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}


	public List<UserRolesDTO> findLoginUserRoles(String username, String accessToken) {
		List<UserRolesDTO> userRolesDTOs = ApiConsume.consumeLoginUserRoles(StaticValueProvider.LOGIN_USER_URI,
				"/roles?username=" + username + "&access_token=" + accessToken);
		return userRolesDTOs;

	}

}
