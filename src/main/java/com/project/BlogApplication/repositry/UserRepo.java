package com.project.BlogApplication.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.BlogApplication.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
Optional<User> findByEmail(String email);
	
}
