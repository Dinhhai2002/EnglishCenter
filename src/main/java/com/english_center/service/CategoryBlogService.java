package com.english_center.service;

import java.util.List;

import com.english_center.entity.CategoryBlog;

public interface CategoryBlogService {
	void create(CategoryBlog categoryBlog) throws Exception;

	CategoryBlog findOne(int id) throws Exception;

	void update(CategoryBlog categoryBlog) throws Exception;

	List<CategoryBlog> getAll(int status) throws Exception;

	CategoryBlog findByName(String categoryName) throws Exception;
}
