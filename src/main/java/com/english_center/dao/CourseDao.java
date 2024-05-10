package com.english_center.dao;

import java.math.BigDecimal;
import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Course;
import com.english_center.model.StoreProcedureListResult;

public interface CourseDao {
	Course findOne(int id) throws Exception;

	List<Course> findAll() throws Exception;

	StoreProcedureListResult<Course> spGCourse(int categoryCourseId, String keySearch, int status, int isPagination,
			Pagination pagination) throws Exception;

	void update(Course course) throws Exception;

	Course spUCreateCourse(String name, String description, BigDecimal price, int isFree) throws Exception;

	Course spUUpdateCourse(int id, String name, int lessons, String description, BigDecimal price, int isFree,
			BigDecimal discountPercent) throws Exception;

	Course findByName(String name) throws Exception;
}
