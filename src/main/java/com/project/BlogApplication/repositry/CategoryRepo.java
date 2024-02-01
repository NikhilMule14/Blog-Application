package com.project.BlogApplication.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.BlogApplication.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}

