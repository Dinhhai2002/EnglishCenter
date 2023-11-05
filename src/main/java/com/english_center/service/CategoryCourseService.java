package com.english_center.service;

import java.util.List;

import com.english_center.entity.CategoryCourse;

public interface CategoryCourseService {
	void create(CategoryCourse categoryCourse) throws Exception;

	CategoryCourse findOne(int id) throws Exception;

	void update(CategoryCourse categoryCourse) throws Exception;
	
	CategoryCourse findByName(String name) throws Exception;
	
	public List<CategoryCourse> findAllHavePagination(int page, int limit) throws Exception;
	
	public List<CategoryCourse> findAll() throws Exception;
}
