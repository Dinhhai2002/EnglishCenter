package com.english_center.dao;

import java.util.List;

import com.english_center.entity.CategoryCourse;

public interface CategoryCourseDao {

	void create(CategoryCourse categoryCourse) throws Exception;

	CategoryCourse findOne(int id) throws Exception;

	void update(CategoryCourse categoryCourse) throws Exception;

	CategoryCourse findByName(String name) throws Exception;

	public List<CategoryCourse> findAllHavePagination(int page, int limit) throws Exception;
	
	public List<CategoryCourse> findAll() throws Exception;
}
