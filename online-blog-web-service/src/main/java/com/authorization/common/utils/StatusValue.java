package com.authorization.common.utils;

public enum StatusValue {
	
	ACTIVE("Active", 1, true),
	INACTIVE("Inactive", 0, false),
	LIKE("Like", 1, true),
	DISLIKE("Dislike", 0, false);
	
	private final String name;
	
	private final int value;
	
	private final boolean status;

	private StatusValue(String name, int value, boolean status) {
		this.name = name;
		this.value = value;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public boolean getStatus() {
		return status;
	}
	
	
}
