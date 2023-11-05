package com.english_center.dao;

import java.util.List;

import com.english_center.entity.TopicExam;

public interface TopicExamDao {
	void create(TopicExam topicExam) throws Exception;

	TopicExam findOne(int id) throws Exception;

	void update(TopicExam topicExam) throws Exception;

	List<TopicExam> getAll() throws Exception;

	List<TopicExam> findByCategoryExamId(int categoryExamId) throws Exception;

	TopicExam findByName(String name) throws Exception;
}
