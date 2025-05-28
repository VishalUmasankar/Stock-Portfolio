package com.stockportfolio.dto;

public class LoginResponse {
	private long id;
    private String username;
    private String email;

    public LoginResponse(long id, String username, String email) {
    	this.id = id;
        this.username = username;
        this.email = email;
    }
    
    public long getId() {
    	return id;
    }

	public String getUsername() {
		return username;
	}

	public String getemail() {
		return email;
	}
	
}

