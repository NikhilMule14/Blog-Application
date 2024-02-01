package com.project.BlogApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;

import com.project.BlogApplication.entity.User;
import com.project.BlogApplication.exceptions.ApiException;
import com.project.BlogApplication.payloder.JwtAuthRequest;
import com.project.BlogApplication.payloder.JwtAuthResponse;
import com.project.BlogApplication.payloder.UserDto;
import com.project.BlogApplication.security.JwtTokenHelper;
import com.project.BlogApplication.service.UserService;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;



@RestController
@RequestMapping("/api/v1/auth/")
@Configuration
public class AuthContoller {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.genrateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);

	    // Manually set properties of UserDto
	    User user = (User) userDetails;
	    UserDto userDto = new UserDto();
	    userDto.setId(user.getId());
	    userDto.setName(user.getName());
	    userDto.setEmail(user.getEmail());
	    userDto.setAbout(user.getAbout());
	    userDto.setPassword(user.getPassword());
	    // Set other properties as needed

	    response.setUser(userDto);
		
		

		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.err.println("Invalid Detials...");
			throw new ApiException("Invalid username or Password");
		}

	}

	// register new user api

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);

	}
	@Bean
	public FilterRegistrationBean corsFilter() {
		
	 UrlBasedCorsConfigurationSource source = new  UrlBasedCorsConfigurationSource();
	    CorsConfiguration corsConfiguration = new CorsConfiguration();

	    corsConfiguration.setAllowCredentials(true);
	    corsConfiguration.addAllowedOriginPattern("*");
	    corsConfiguration.addAllowedHeader("Authorization");
	    corsConfiguration.addAllowedHeader("Content-Type");
	    corsConfiguration.addAllowedHeader("Accept");
	    corsConfiguration.addAllowedMethod("POST");
	    corsConfiguration.addAllowedMethod("GET");
	    corsConfiguration.addAllowedMethod("DELETE");
	    corsConfiguration.addAllowedMethod("PUT");
	    corsConfiguration.addAllowedMethod("OPTIONS");
	    corsConfiguration.setMaxAge(3600L);

	    source.registerCorsConfiguration("/**",corsConfiguration);
      FilterRegistrationBean bean = new FilterRegistrationBean (new CorsFilter(source));
              // Make sure it's the first filter to be executed
	    return bean;
	}
	
	}

