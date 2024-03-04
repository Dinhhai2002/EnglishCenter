package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.ReplyCommentsDao;
import com.english_center.entity.ReplyComments;
import com.english_center.service.ReplyCommentsService;

@Service("ReplyCommentsService")
@Transactional(rollbackFor = Error.class)
public class ReplyCommentsServiceImpl implements ReplyCommentsService {
	@Autowired
	ReplyCommentsDao replyCommentsDao;

	@Override
	public void create(ReplyComments replyComments) throws Exception {
		replyCommentsDao.create(replyComments);
	}

	@Override
	public ReplyComments findOne(int id) throws Exception {
		return replyCommentsDao.findOne(id);
	}

	@Override
	public void update(ReplyComments replyComments) throws Exception {
		replyCommentsDao.update(replyComments);
	}

	@Override
	public List<ReplyComments> findByCommentId(int commentsId) throws Exception {
		return replyCommentsDao.findByCommentId(commentsId);
	}

	@Override
	public void delete(ReplyComments replyComments) throws Exception {
		replyCommentsDao.delete(replyComments);
	}

	@Override
	public List<ReplyComments> getAll() throws Exception {
		return replyCommentsDao.getAll();
	}

}
