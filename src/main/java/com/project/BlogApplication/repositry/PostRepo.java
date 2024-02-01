package com.project.BlogApplication.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.BlogApplication.entity.Category;
import com.project.BlogApplication.entity.Post;
import com.project.BlogApplication.entity.User;

public interface  PostRepo extends JpaRepository<Post, Integer>{
     
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	@Query("SELECT p FROM Post p WHERE p.title LIKE :title")
    List<Post> searchByTitle(@Param("title") String title);
	
}
