package com.english_center.service;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.CategoryExam;
import com.english_center.model.StoreProcedureListResult;

public interface CategoryExamService {
	void create(CategoryExam categoryExam) throws Exception;

	CategoryExam findOne(int id) throws Exception;
	
	List<CategoryExam> getAll() throws Exception;

	void update(CategoryExam categoryExam) throws Exception;
	
	CategoryExam findByName(String name) throws Exception;

	StoreProcedureListResult<CategoryExam> spGListCategoryExam(String keySearch,int status,Pagination pagination) throws Exception;
}
