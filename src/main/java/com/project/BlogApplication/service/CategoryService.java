package com.project.BlogApplication.service;

import java.util.List;

import com.project.BlogApplication.payloder.CategoryDto;

public interface CategoryService {
	// Create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// delete
	public void deleteCategory(Integer categoryId);

	// get
	public CategoryDto getCategory(Integer categoryId);

	// getAll
	List<CategoryDto>getCategories();
}
