package com.project.BlogApplication.payloder;

import lombok.Data;

@Data
public class JwtAuthRequest {
	private String username;

	private String password;
}
