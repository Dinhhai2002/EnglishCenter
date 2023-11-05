package com.english_center.service;

import java.util.List;

import com.english_center.entity.TopicExam;

public interface TopicExamService {
	void create(TopicExam topicExam) throws Exception;

	TopicExam findOne(int id) throws Exception;
	
	List<TopicExam> getAll() throws Exception;
	
	List<TopicExam> findByCategoryExamId(int categoryExamId) throws Exception;

	void update(TopicExam topicExam) throws Exception;
	
	TopicExam findByName(String name) throws Exception;
}
