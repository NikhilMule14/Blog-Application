package com.project.BlogApplication.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.BlogApplication.entity.Comment;
import com.project.BlogApplication.entity.Post;
import com.project.BlogApplication.exceptions.ResourceNotFoundException;
import com.project.BlogApplication.payloder.CommentDto;
import com.project.BlogApplication.repositry.CommentRepo;
import com.project.BlogApplication.repositry.PostRepo;
import com.project.BlogApplication.service.CommentService;
@Service
public class CommentServiceIMPL implements CommentService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private Comment comment;	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {


	  Post post= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
	  
	  this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
		
		
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com= this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));

		this.commentRepo.delete(com);
	}

}
