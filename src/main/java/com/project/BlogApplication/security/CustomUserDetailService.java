package com.project.BlogApplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.BlogApplication.entity.User;
import com.project.BlogApplication.exceptions.ResourceNotFoundException;
import com.project.BlogApplication.repositry.UserRepo;
@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// loadding use4 from database by username

		User user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "email" + username, 0));

		return user;
	}

}
