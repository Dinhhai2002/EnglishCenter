package com.english_center.service;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Exam;
import com.english_center.model.StoreProcedureListResult;

public interface ExamService {
	void create(Exam exam) throws Exception;

	Exam findOne(int id) throws Exception;

	void update(Exam exam) throws Exception;

	Exam findByName(String name) throws Exception;

	List<Exam> findByTopicId(int topicId) throws Exception;

	List<Exam> findByCategory(int categoryExamId) throws Exception;

	StoreProcedureListResult<Exam> spGListExam(int categoryExamId, int topicExamId, String keySearch, int status,
			Pagination pagination, int isPagination) throws Exception;

}
