package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.ExamDao;
import com.english_center.entity.Exam;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.ExamService;

@Service("ExamService")
@Transactional(rollbackFor = Error.class)
public class ExamServiceImpl implements ExamService {

	@Autowired
	ExamDao examDao;

	@Override
	public void create(Exam exam) throws Exception {
		examDao.create(exam);

	}

	@Override
	public Exam findOne(int id) throws Exception {
		return examDao.findOne(id);
	}

	@Override
	public void update(Exam exam) throws Exception {
		examDao.update(exam);

	}

	@Override
	public Exam findByName(String name) throws Exception {
		return examDao.findByName(name);
	}

	@Override
	public List<Exam> findByTopicId(int topicId) throws Exception {

		return examDao.findByTopicId(topicId);
	}

	@Override
	public List<Exam> findByCategory(int categoryExamId) throws Exception {
		return examDao.findByCategory(categoryExamId);
	}

	@Override
	public StoreProcedureListResult<Exam> spGListExam(int categoryExamId, int topicExamId, String keySearch, int status,
			Pagination pagination, int isPagination) throws Exception {
		return examDao.spGListExam(categoryExamId, topicExamId, keySearch, status, pagination.getLimit(),
				pagination.getOffset(), isPagination);
	}

}
