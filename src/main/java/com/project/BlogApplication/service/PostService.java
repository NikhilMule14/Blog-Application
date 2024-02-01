package com.project.BlogApplication.service;

import java.util.List;

import com.project.BlogApplication.entity.Post;
import com.project.BlogApplication.payloder.PostDto;
import com.project.BlogApplication.payloder.PostResponse;

public interface PostService {

	// create

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer PostId);

	// delete
	void deletePost(Integer PostId);

	// get all post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get single Post

	PostDto getPostById(Integer postId);

	// get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);

	// get all posts by user
	List<PostDto> getPostByUser(Integer userId);

	// get search posts
	List<PostDto> searchPosts(String keyword);
}
