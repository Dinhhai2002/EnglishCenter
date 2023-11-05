package com.english_center.service;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Lessons;
import com.english_center.model.StoreProcedureListResult;

public interface LessonsService {
	void create(Lessons lessons) throws Exception;

	Lessons findOne(int id) throws Exception;

	void update(Lessons lessons) throws Exception;

	List<Lessons> findByChapterId(int chapterId) throws Exception;

	StoreProcedureListResult<Lessons> spGListLessons(int courseId, int chapterId, String keySearch, int status,
			Pagination pagination, int isPagination) throws Exception;
}
