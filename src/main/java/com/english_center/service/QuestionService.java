package com.english_center.service;

import java.util.List;

import com.english_center.entity.Question;

public interface QuestionService {
	void create(Question question) throws Exception;

	Question findOne(int id) throws Exception;

	void update(Question question) throws Exception;
	
	List<Question> getListByExamId(int examId) throws Exception;
	
	Question findOneBySortAndExamId(int sort, int examId) throws Exception;
	
	List<Question> getAll() throws Exception;
}
