package com.stockportfolio.dto;

public class LoginResponse {
    private String username;
    private String email;

    public LoginResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}
	
}

