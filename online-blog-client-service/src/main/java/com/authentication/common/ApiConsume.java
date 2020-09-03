package com.authentication.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.authentication.user.model.BlogDTO;
import com.authentication.user.model.LikeAndDislikeDTO;
import com.authentication.user.model.UserRolesDTO;
import com.authentication.user.model.UsersDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ApiConsume {

	public static List<UserRolesDTO> consumeLoginUserRoles(String uri, String uriExtension) {
		RestTemplate template = new RestTemplate();
		System.out.println(StaticValueProvider.BASE_URL + uri + uriExtension);
		ResponseEntity<UserRolesDTO[]> response = template
				.getForEntity(StaticValueProvider.BASE_URL + uri + uriExtension, UserRolesDTO[].class);
		List<UserRolesDTO> userDtos = Arrays.asList(response.getBody());
		return new ArrayList<>(userDtos);
	}

	public static ResponseEntity<UsersDTO> exposeLoginUsersDTO(String uri, String uriExtension, UsersDTO usersDTO) {
		RestTemplate template = new RestTemplate();
		HttpEntity<UsersDTO> request = new HttpEntity<>(usersDTO);
		String url = StaticValueProvider.BASE_URL + uri + uriExtension;
		return template.exchange(url, HttpMethod.POST, request, UsersDTO.class);
	}

	public static UsersDTO consumeLoginUsersInfo(String uri, String uriExtension) {
		RestTemplate template = new RestTemplate();
		System.out.println(StaticValueProvider.BASE_URL + uri + uriExtension);
		ResponseEntity<UsersDTO> response = template.getForEntity(StaticValueProvider.BASE_URL + uri + uriExtension,
				UsersDTO.class);
		return response.getBody().getUsername() != null ? response.getBody() : null;
	}

	public static void exposeStringurlByPost(String uri) throws UnirestException {
		Unirest.post(uri).header("cache-control", "no-cache")
				.header("postman-token", "a1c3472b-16c0-0c41-2d8d-d1f429be1956").asString();
	}

	public static void exposeStringurlByDelete(String uri) throws UnirestException {
		Unirest.delete(uri).asString();
	}

	public static List<UsersDTO> consumeLoginUsersInfoList(String uri, String uriExtension) {
		RestTemplate template = new RestTemplate();
		ResponseEntity<UsersDTO[]> response = template.getForEntity(StaticValueProvider.BASE_URL + uri + uriExtension,
				UsersDTO[].class);
		return Arrays.asList(response.getBody());
	}

	public static void exposeBlogDTO(String uri, String uriExtension, BlogDTO blogDTO) {
		RestTemplate template = new RestTemplate();
		HttpEntity<BlogDTO> request = new HttpEntity<>(blogDTO);
		String url = StaticValueProvider.BASE_URL + uri + uriExtension;
		template.exchange(url, HttpMethod.POST, request, BlogDTO.class);
	}

	public static List<BlogDTO> consumeBlogDTO(String uri, String uriExtension) {
		RestTemplate template = new RestTemplate();
		System.out.println(StaticValueProvider.BASE_URL + uri + uriExtension);
		ResponseEntity<BlogDTO[]> response = template.getForEntity(StaticValueProvider.BASE_URL + uri + uriExtension,
				BlogDTO[].class);
		return Arrays.asList(response.getBody());
	}
	
	public static void exposeLikeDislikeDTO(String uri, String uriExtension, LikeAndDislikeDTO likeAndDislikeDTO) {
		RestTemplate template = new RestTemplate();
		HttpEntity<LikeAndDislikeDTO> request = new HttpEntity<>(likeAndDislikeDTO);
		String url = StaticValueProvider.BASE_URL + uri + uriExtension;
		template.exchange(url, HttpMethod.POST, request, LikeAndDislikeDTO.class);
	}

	public static void exposeBlogDTOWithPatch(String uri, String uriExtension, BlogDTO blogDTO) {

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "merge-patch+json");
		headers.setContentType(mediaType);

		HttpEntity<BlogDTO> request = new HttpEntity<>(blogDTO, headers);
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		String url = StaticValueProvider.BASE_URL + uri + uriExtension;
		restTemplate.exchange(url, HttpMethod.PATCH, request, Void.class);

	}
}
