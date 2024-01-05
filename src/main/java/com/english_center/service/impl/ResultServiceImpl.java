package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.ResultDao;
import com.english_center.entity.Result;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.model.StoreProcedureResult;
import com.english_center.service.ResultService;

@Service("ResultService")
@Transactional(rollbackFor = Error.class)
public class ResultServiceImpl implements ResultService {

	@Autowired
	ResultDao resultDao;

	@Override
	public void create(Result result) throws Exception {
		resultDao.create(result);

	}

	@Override
	public Result findOne(int id) throws Exception {
		return resultDao.findOne(id);
	}

	@Override
	public void update(Result result) throws Exception {
		resultDao.update(result);

	}

	@Override
	public List<Result> findByUserId(int userId) throws Exception {
		return resultDao.findByUserId(userId);
	}

	@Override
	public List<Result> findByExamId(int examId) throws Exception {
		return resultDao.findByExamId(examId);
	}

	@Override
	public StoreProcedureListResult<Result> spGResult(int userId, int examId, String keySearch, Pagination pagination)
			throws Exception {
		return resultDao.spGResult(userId, examId, keySearch, pagination);
	}

	@Override
	public StoreProcedureResult<Result> spUCreateResult(int userId, int examId, String listAnswerJson,
			String timeComplete, int totalQuestionSkip) throws Exception {
		return resultDao.spUCreateResult(userId, examId, listAnswerJson, timeComplete, totalQuestionSkip);
	}

}
