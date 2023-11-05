package com.english_center.dao;

import java.util.List;

import com.english_center.entity.Question;

public interface QuestionDao {
	void create(Question question) throws Exception;

	Question findOne(int id) throws Exception;

	void update(Question question) throws Exception;
	
	List<Question> getListByExamId(int examId) throws Exception;
}
