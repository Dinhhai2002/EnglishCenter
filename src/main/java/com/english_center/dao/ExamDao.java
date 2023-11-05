package com.english_center.dao;

import java.util.List;

import com.english_center.entity.Exam;
import com.english_center.model.StoreProcedureListResult;

public interface ExamDao {
	void create(Exam exam) throws Exception;

	Exam findOne(int id) throws Exception;

	List<Exam> findByTopicId(int topicId) throws Exception;

	StoreProcedureListResult<Exam> spGListExam(int categoryExamId, int topicExamId, String keySearch, int status,
			int limit, int page, int isPagination) throws Exception;

	List<Exam> findByCategory(int categoryExamId) throws Exception;

	void update(Exam exam) throws Exception;

	Exam findByName(String name) throws Exception;
}
