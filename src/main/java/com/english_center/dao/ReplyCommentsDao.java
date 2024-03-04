package com.english_center.dao;

import java.util.List;

import com.english_center.entity.ReplyComments;

public interface ReplyCommentsDao {
	void create(ReplyComments replyComments) throws Exception;

	ReplyComments findOne(int id) throws Exception;

	void update(ReplyComments replyComments) throws Exception;

	List<ReplyComments> findByCommentId(int commentsId) throws Exception;

	void delete(ReplyComments replyComments) throws Exception;

	List<ReplyComments> getAll() throws Exception;
}
