package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.CategoryCourseDao;
import com.english_center.entity.CategoryCourse;
import com.english_center.service.CategoryCourseService;

@Service("CategoryCourseService")
@Transactional(rollbackFor = Error.class)
public class CategoryCourseServiceImpl implements CategoryCourseService{
	
	@Autowired
	CategoryCourseDao categoryCourseDao;

	@Override
	public void create(CategoryCourse categoryCourse) throws Exception {
		categoryCourseDao.create(categoryCourse);
	}

	@Override
	public CategoryCourse findOne(int id) throws Exception {
		
		return categoryCourseDao.findOne(id);
	}

	@Override
	public void update(CategoryCourse categoryCourse) throws Exception {
		categoryCourseDao.update(categoryCourse);
		
	}

	@Override
	public CategoryCourse findByName(String name) throws Exception {
		return categoryCourseDao.findByName(name);
	}

	@Override
	public List<CategoryCourse> findAllHavePagination(int page, int limit) throws Exception {
		return categoryCourseDao.findAllHavePagination(page, limit);
	}

	@Override
	public List<CategoryCourse> findAll() throws Exception {
		return categoryCourseDao.findAll();
	}
	
	
}
