package com.authorization.common.utils;

public enum UserInfo {

	ADMIN("ROLE_ADMIN", 101, "Admin"),
	BLOGGER("ROLE_USER", 202, "Blogger");

	private final String role;

	private final int code;
	
	private final String type;

	private UserInfo(String role, int code, String type) {
		this.role = role;
		this.code = code;
		this.type = type;
	}

	public String getRole() {
		return role;
	}

	public int getCode() {
		return code;
	}
	
	public String getType() {
		return type;
	}

}
