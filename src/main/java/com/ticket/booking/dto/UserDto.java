package com.ticket.booking.dto;

public class UserDto {

	private long userId;
	
	private String userName;
	
	private String userType;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public UserDto(long userId, String userName, String userType) {
		this.userId = userId;
		this.userName = userName;
		this.userType = userType;
	}
	
}
