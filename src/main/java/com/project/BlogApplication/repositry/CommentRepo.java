package com.project.BlogApplication.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.BlogApplication.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
