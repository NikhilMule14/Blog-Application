package com.project.BlogApplication.payloder;

import lombok.Data;

@Data
public class JwtAuthResponse {
	
	private String token;
	
	private UserDto user;

}
