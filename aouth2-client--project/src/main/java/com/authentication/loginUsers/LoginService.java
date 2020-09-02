package com.authentication.loginUsers;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class LoginService {

	public String porvideAccessToken(String username, String password) {

		try {
			HttpResponse<String> response = Unirest.post(LoginUtils.OAUTH_TOKEN_URL)
					.header("content-type", LoginUtils.OAUTH_CONTENT_TYPE)
					.header("authorization", LoginUtils.OAUTH_AUTHORIZATION_HEADER)
					.header("cache-control", LoginUtils.OAUTH_CACHE_CONTROL)
					.body(LoginUtils.getOauthBody(username, password)).asString();
			
		


			if (response.getStatus() == 200) {

				JSONObject jSONObject = new JSONObject(response.getBody());
				return jSONObject.getString("access_token");
				
			} else {
				return null;
			}

		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void logout() {

	}

}
