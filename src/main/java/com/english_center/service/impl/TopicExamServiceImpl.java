package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.TopicExamDao;
import com.english_center.entity.TopicExam;
import com.english_center.service.TopicExamService;

@Service("TopicExamService")
@Transactional(rollbackFor = Error.class)
public class TopicExamServiceImpl implements TopicExamService {
	@Autowired
	TopicExamDao topicExamDao;

	@Override
	public void create(TopicExam topicExam) throws Exception {
		topicExamDao.create(topicExam);
	}

	@Override
	public TopicExam findOne(int id) throws Exception {
		return topicExamDao.findOne(id);
	}

	@Override
	public void update(TopicExam topicExam) throws Exception {
		topicExamDao.update(topicExam);
	}

	@Override
	public TopicExam findByName(String name) throws Exception {
		return topicExamDao.findByName(name);
	}

	@Override
	public List<TopicExam> getAll() throws Exception {
		return topicExamDao.getAll();
	}

	@Override
	public List<TopicExam> findByCategoryExamId(int categoryExamId) throws Exception {
		return topicExamDao.findByCategoryExamId(categoryExamId);
	}
}
