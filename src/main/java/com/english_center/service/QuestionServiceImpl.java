package com.english_center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.QuestionDao;
import com.english_center.entity.Question;

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
	

}
