package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.CommentsDao;
import com.english_center.entity.Comments;
import com.english_center.service.CommentsService;

@Service("CommentsService")
@Transactional(rollbackFor = Error.class)
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	CommentsDao commentsDao;

	@Override
	public void create(Comments comments) throws Exception {
		commentsDao.create(comments);
	}

	@Override
	public Comments findOne(int id) throws Exception {
		return commentsDao.findOne(id);
	}

	@Override
	public void update(Comments comments) throws Exception {
		commentsDao.update(comments);
	}

	@Override
	public List<Comments> findByExamId(int examId) throws Exception {

		return commentsDao.findByExamId(examId);
	}

	@Override
	public void delete(Comments comments) throws Exception {
		commentsDao.delete(comments);
	}

	@Override
	public int countComments(int examId) throws Exception {
		return commentsDao.countComments(examId);
	}

}
