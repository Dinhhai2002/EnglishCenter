package com.english_center.service;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Class;
import com.english_center.model.StoreProcedureListResult;

public interface ClassService {
	void create(Class _class) throws Exception;

	Class findOne(int id) throws Exception;

	void update(Class _class) throws Exception;

	List<Class> getAllByTeacherId(int teacherId) throws Exception;

	List<Class> getAllByCourseId(int courseId) throws Exception;

	List<Class> getAll() throws Exception;

	StoreProcedureListResult<Class> spGClass(int courseId, int teacherId, String keySearch, int status,
			Pagination pagination) throws Exception;
}
