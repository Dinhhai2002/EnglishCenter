package com.english_center.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.CourseDao;
import com.english_center.entity.Course;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.CourseService;

@Service("CourseService")
@Transactional(rollbackFor = Error.class)
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseDao courseDao;

	@Override
	public Course findOne(int id) throws Exception {
		return courseDao.findOne(id);
	}

	@Override
	public void update(Course course) throws Exception {
		courseDao.update(course);
	}

	@Override
	public Course spUCreateCourse(String name, String description, BigDecimal price, int isFree) throws Exception {
		return courseDao.spUCreateCourse(name, description, price, isFree);
	}

	@Override
	public Course spUUpdateCourse(int id, String name, int lessons, String description, BigDecimal price, int isFree,
			BigDecimal discountPercent) throws Exception {
		return courseDao.spUUpdateCourse(id, name, lessons, description, price, isFree, discountPercent);
	}

	@Override
	public List<Course> findAll() throws Exception {
		return courseDao.findAll();
	}

	@Override
	public StoreProcedureListResult<Course> spGCourse(int categoryCourseId, String keySearch, int status,
			int isPagination, Pagination pagination) throws Exception {
		return courseDao.spGCourse(categoryCourseId, keySearch, status, isPagination, pagination);
	}

	@Override
	public Course findByName(String name) throws Exception {
		return courseDao.findByName(name);
	}

}
