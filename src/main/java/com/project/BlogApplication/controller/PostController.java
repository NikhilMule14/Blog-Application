package com.project.BlogApplication.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.BlogApplication.config.AppConstants;
import com.project.BlogApplication.entity.Post;
import com.project.BlogApplication.payloder.ApiResponce;
import com.project.BlogApplication.payloder.PostDto;
import com.project.BlogApplication.payloder.PostResponse;
import com.project.BlogApplication.service.FileService;
import com.project.BlogApplication.service.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	// getby user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {

		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	// get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {

		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	// getall post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)

	{
		PostResponse postResponce = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponce, HttpStatus.OK);
	}

	// get post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	// get delete post by id
	@DeleteMapping("/posts/{postId}")
	public ApiResponce deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponce("Post is Successfully Deleted ..", true);
	}

	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	// search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keyword)

	{
		List<PostDto> result = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}
	// post image upload

	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uplodePostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);

		postDto.setImageName(fileName);

		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);

	}

	// method to server file
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
