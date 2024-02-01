package com.project.BlogApplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.BlogApplication.payloder.ApiResponce;
import com.project.BlogApplication.payloder.CategoryDto;
import com.project.BlogApplication.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	// create

	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory( @Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer catId) {
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponce> deleteCategory(@PathVariable Integer catId) {
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponce>(new ApiResponce("category is deleted successfully !!..", true),
				HttpStatus.OK);
	}

	// get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		CategoryDto categoryDto = this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	// getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<CategoryDto> categories = this.categoryService.getCategories();
		return ResponseEntity.ok(categories);
	}

}
