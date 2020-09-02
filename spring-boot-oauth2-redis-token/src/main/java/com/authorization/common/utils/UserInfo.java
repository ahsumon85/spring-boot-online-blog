package com.authorization.common.utils;

public enum UserInfo {

	ADMIN("ROLE_ADMIN", 101),
	BLOGGER("ROLE_USER", 202);

	private final String name;

	private final int code;

	private UserInfo(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}

}
