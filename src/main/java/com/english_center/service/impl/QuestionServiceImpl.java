package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.QuestionDao;
import com.english_center.entity.Question;
import com.english_center.service.QuestionService;

@Service("QuestionService")
@Transactional(rollbackFor = Error.class)
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	QuestionDao questionDao;

	@Override
	public void create(Question question) throws Exception {
		questionDao.create(question);

	}

	@Override
	public Question findOne(int id) throws Exception {
		return questionDao.findOne(id);
	}

	@Override
	public void update(Question question) throws Exception {
		questionDao.update(question);

	}

	@Override
	public List<Question> getListByExamId(int examId) throws Exception {
		return questionDao.getListByExamId(examId);
	}

	@Override
	public Question findOneBySortAndExamId(int sort, int examId) throws Exception {
		return questionDao.findOneBySortAndExamId(sort, examId);
	}

	@Override
	public List<Question> getAll() throws Exception {
		return questionDao.getAll();
	}

}
