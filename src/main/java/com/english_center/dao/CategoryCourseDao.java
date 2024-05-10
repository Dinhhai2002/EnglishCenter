package com.english_center.dao;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.CategoryCourse;
import com.english_center.model.StoreProcedureListResult;

public interface CategoryCourseDao {

	void create(CategoryCourse categoryCourse) throws Exception;

	CategoryCourse findOne(int id) throws Exception;

	void update(CategoryCourse categoryCourse) throws Exception;

	CategoryCourse findByName(String name) throws Exception;

//	public List<CategoryCourse> findAllHavePagination(int page, int limit) throws Exception;
	StoreProcedureListResult<CategoryCourse> spGListCategoryCourse(String keySearch,int status,Pagination pagination) throws Exception;
	
	public List<CategoryCourse> findAll() throws Exception;
}
