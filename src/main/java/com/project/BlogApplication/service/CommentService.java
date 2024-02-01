package com.project.BlogApplication.service;

import com.project.BlogApplication.payloder.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId);

	void deleteComment(Integer commentId);
}
