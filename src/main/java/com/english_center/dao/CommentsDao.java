package com.english_center.dao;

import java.util.List;

import com.english_center.entity.Comments;

public interface CommentsDao {
	void create(Comments comments) throws Exception;

	Comments findOne(int id) throws Exception;

	void update(Comments comments) throws Exception;

	List<Comments> findByExamId(int examId) throws Exception;

	void delete(Comments comments) throws Exception;

	int countComments(int examId) throws Exception;
}
