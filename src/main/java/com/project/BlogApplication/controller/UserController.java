package com.project.BlogApplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.BlogApplication.payloder.ApiResponce;
import com.project.BlogApplication.payloder.UserDto;
import com.project.BlogApplication.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	private UserService userService;

	// Post- create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	// PUT-update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
		UserDto updateUser = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updateUser);
	}

	//ADMIN
	// DELETE -delete User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponce> deleteUser(@PathVariable("userId") Integer uid) {
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponce>(new ApiResponce("User deleted Successfully", true), HttpStatus.OK);
	}

	// GET -users get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUser());
	}

	// GET -Single_user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
