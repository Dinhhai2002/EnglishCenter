package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.CategoryExamDao;
import com.english_center.entity.CategoryExam;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.CategoryExamService;

@Service("CategoryExamService")
@Transactional(rollbackFor = Error.class)
public class CategoryExamServiceImpl implements CategoryExamService {

	@Autowired
	CategoryExamDao categoryExamDao;

	@Override
	public void create(CategoryExam categoryExam) throws Exception {
		categoryExamDao.create(categoryExam);

	}

	@Override
	public CategoryExam findOne(int id) throws Exception {
		return categoryExamDao.findOne(id);
	}

	@Override
	public void update(CategoryExam categoryExam) throws Exception {
			categoryExamDao.update(categoryExam);
	}

	@Override
	public CategoryExam findByName(String name) throws Exception {
		return categoryExamDao.findByName(name);
	}

	@Override
	public List<CategoryExam> getAll() throws Exception {
		return categoryExamDao.getAll();
	}

	@Override
	public StoreProcedureListResult<CategoryExam> spGListCategoryExam(String keySearch, int status,
			Pagination pagination) throws Exception {
		return categoryExamDao.spGListCategoryExam(keySearch, status, pagination);
	}

}
